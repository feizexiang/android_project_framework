package com.fzx.framework.utils;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.fzx.framework.utils.DLog;
import com.fzx.framework.utils.DiskLruCacheHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 * 系统崩溃
 *
 * @author fx
 */
public class MyCrashHandler implements UncaughtExceptionHandler {
    public static final String CRASH = "crash_msg_info";
    public static final String CRASH_FILENAME = "crash_msg_info_file";
    public static final String CRASH_FILEPATH = "crash_msg_info_filepath";
    public static final String TAG = "MyCrashHandler";

    private Context mContext;

    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
//	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private DateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");


    public MyCrashHandler(Context context) {
        this.mContext = context;

    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            // 收集设备参数信息
            collectDeviceInfo(mContext);
            // 保存日志文件
            String fileName = saveCrashInfo2File(ex);
            String filePath = DiskLruCacheHelper.getCacheDir(DiskLruCacheHelper.CacheType.CRASH).getAbsolutePath();
            //记录崩溃日志信息，下次启动时 提醒上传崩溃报告
            saveCrashFileName(fileName, filePath);
            reStartApp();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            DLog.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                DLog.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                DLog.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     */
    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            DLog.e("crash", sb.toString());
//			long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());

//			String fileName = time + "-" + timestamp;
            DiskLruCacheHelper.set(DiskLruCacheHelper.CacheType.CRASH, time, sb.toString());
            return time;
        } catch (Exception e) {
            DLog.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

    /**
     * 保存最新的崩溃日志文件名和路径（路径包括文件名）
     *
     * @param fileName
     * @param filePath
     */
    public void saveCrashFileName(String fileName, String filePath) {
        SharedPreferences sp = mContext.getSharedPreferences(CRASH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CRASH_FILENAME, fileName);
        editor.putString(CRASH_FILEPATH, filePath);
        editor.commit();
    }

    /**
     * 重启app
     */
    public void reStartApp() {
        // 重新启动程序，注释上面的退出程序
        Intent intent = new Intent();
        intent.setClass(mContext, LauncherActivity.class);
        intent.putExtra("flag", "crash");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
