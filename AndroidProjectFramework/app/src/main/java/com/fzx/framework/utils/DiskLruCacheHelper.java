package com.fzx.framework.utils;

import android.content.Context;
import android.os.Environment;

import com.fzx.framework.application.ControlApplication;
import com.jakewharton.disklrucache.DiskLruCache;


import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 磁盘存储
 */
public class DiskLruCacheHelper {
    private static final long MAX_SIZE = 1024 * 1024 * 10; // 10MB
    private static final Map<CacheType, DiskLruCache> caches = new ConcurrentHashMap<CacheType, DiskLruCache>();

    private DiskLruCacheHelper() {
    }

    /**
     * 重置缓存
     */
    public synchronized static void clear() {
        for (CacheType type : CacheType.values()) {
            get(type, "0");
        }

        Set<CacheType> clearTypes = new HashSet<CacheType>();

        for (Map.Entry<CacheType, DiskLruCache> entry : caches.entrySet()) {
            if (entry.getKey().isClearable()) {
                try {
                    entry.getValue().delete();
                    clearTypes.add(entry.getKey());
                } catch (Throwable e) {
                    DLog.e("error", "clear cache", e);
                }
            }
        }

        for (CacheType type : clearTypes) {
            caches.remove(type);
        }
    }

    public synchronized static void set(CacheType type, String key, String value) {
        DiskLruCache cache = null;
        DiskLruCache.Editor editor = null;

        try {
            if (!caches.containsKey(type)) {
                caches.put(type, DiskLruCache.open(getCacheDir(type), 1, 1, MAX_SIZE));
            }

            cache = caches.get(type);
            editor = cache.edit(key);

            if (null != editor) {
                cache.remove(key);
                editor.set(0, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != editor) {
                try {
                    editor.commit();
                } catch (IOException e) {
                }
            }

            if (null != cache) {
                try {
                    cache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized static String get(CacheType type, String key) {
        String value = null;

        DiskLruCache cache = null;

        try {
            if (!caches.containsKey(type)) {
                caches.put(type, DiskLruCache.open(getCacheDir(type), 1, 1, MAX_SIZE));
            }

            cache = caches.get(type);
            DiskLruCache.Snapshot snapshot = cache.get(key);

            if (null != snapshot) {
                value = snapshot.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cache) {
                try {
                    cache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return value;
    }


    public synchronized static File getCacheDir(CacheType type) {

        String cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.isExternalStorageRemovable()) {
            cacheDir = getExternalCacheDir(ControlApplication.getAppContext());
            if (cacheDir == null) {//部分机型返回了null
                cacheDir = getInternalCacheDir(ControlApplication.getAppContext());
            }
        } else {
            cacheDir = getInternalCacheDir(ControlApplication.getAppContext());
        }

        File path = new File(cacheDir + File.separator + type.name().toLowerCase());

        if (!path.exists()) {
            path.mkdirs();
        }

        return path;
    }

    private static String getExternalCacheDir(Context context) {
        File dir = context.getExternalCacheDir();
        if (dir == null)
            return null;
        if (!dir.exists())
            dir.mkdirs();
        return dir.getPath();
    }

    private static String getInternalCacheDir(Context context) {
        File dir = context.getCacheDir();
        if (!dir.exists())
            dir.mkdirs();
        return dir.getPath();
    }

    public enum CacheType {
        /**
         * 图片
         */
        IMAGES(true),
        /**
         * 资讯列表
         */
        INFORMATION(true),
        /**
         * 资讯详情
         */
        INFODETAIL(true),
        /**
         * 崩溃日志
         */
        CRASH(false);

        private boolean value;

        CacheType(boolean value) {
            this.value = value;
        }

        public boolean isClearable() {
            return value;
        }
    }
}
