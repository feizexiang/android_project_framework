package com.fzx.framework.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

import com.example.myapplication.R;
import com.fzx.framework.ui.dialog.CustomProgressDialog;
import com.fzx.framework.utils.DLog;


/**
 * 虚拟基础类
 *
 * @author fzx
 * @ClassName: BaseActivity
 * @date 2015年5月18日 上午10:50:03
 */
public abstract class BaseActivity extends FragmentActivity implements OnPushDataChangedListener {
    private static final String TAG = "BaseActivity";

    /**
     * 分辨率的宽
     */
    public int screenwidth;
    /**
     * 分辨率的高
     */
    public int screenheight;
    /**
     * 屏幕密度DPI
     */
    public int densityDpi;

    protected BaseActivity mContext = this;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);


        // 获取手机分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenwidth = dm.widthPixels;
        screenheight = dm.heightPixels;
        densityDpi = dm.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        DLog.i(TAG, "手机分辨率：" + screenwidth + "*" + screenheight);
        DLog.i(TAG, "手机密度DPI：" + densityDpi);
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
        }
    }


    /**
     * 获取布局文件
     *
     * @return 布局文件ID
     */

    protected abstract int getContentLayout();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                this.finish();
                break;
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();
    }

    public void showProgressDialog() {
        if (null == progressDialog) {
            progressDialog = new CustomProgressDialog.Builder(this)
                    .setTitle(this.getString(R.string.dialog_oper_title))
                    .setMessage(this.getString(R.string.dialog_wait_msg))
                    .setCancelable(false).create();
        }
        if (!isFinishing() && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        setProgressDialogCancelable(true);
    }

    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 设置加载框是否可取消
     *
     * @param isCancel
     */
    public void setProgressDialogCancelable(boolean isCancel) {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.setCancelable(isCancel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();//关闭activity前，先关闭dialog
        super.onDestroy();
    }

    @Override
    public void onPushDataChanged(int what, Object obj) {

    }

    private CustomProgressDialog progressDialog = null;
}
