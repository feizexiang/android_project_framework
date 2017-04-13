package com.fzx.framework.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.fzx.framework.utils.MyCrashHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.LinkedList;
import java.util.List;

/**
 * [程序应用]<BR>
 * [程序应用]<BR>
 *
 * @author zoe
 * @version [ScheduleControl, 下午4:57:09]
 */
public class ControlApplication extends Application {
    public static DisplayImageOptions DEFAULTOPTIONS;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (getCurProcessName().equals("com.storemax.ipos.phone")) {
            context = getApplicationContext();
            initImageLoader();

            // 崩溃日志捕获
            Thread.setDefaultUncaughtExceptionHandler(new MyCrashHandler(this));
        }
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        DEFAULTOPTIONS = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(false)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)// int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 淡入

                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                // .memoryCache(new WeakMemoryCache())
                .defaultDisplayImageOptions(DEFAULTOPTIONS).threadPoolSize(3)
                // .diskCacheSize(50 * 1024 * 1024)
                // .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 缓存的文件数量
                // .diskCache(new
                // UnlimitedDiskCache(this.getExternalCacheDir()))
                // You can pass your own disc cache implementation
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext(),
                                5 * 1000, 30 * 1000)).writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 往集合中添加Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 退出
     */
    public void exitToLogin() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    private String getCurProcessName() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    private List<Activity> activityList = new LinkedList<Activity>();

    public static Context getAppContext() {
        return context;
    }

}
