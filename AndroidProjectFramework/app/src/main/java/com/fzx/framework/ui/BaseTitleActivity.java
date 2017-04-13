package com.fzx.framework.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.fzx.framework.application.ControlApplication;


/**
 * 标题
 *
 * @author fzx
 * @ClassName: BaseTitleActivity
 * @date 2015年5月18日 上午11:42:13
 */
public abstract class BaseTitleActivity extends BaseActivity {
    protected RelativeLayout rvTitle;
    /**
     * 内容界面--集成后向其内部添加
     */
    private LinearLayout mContentView;
    /**
     * 左按钮
     */
    protected LinearLayout mTitleLeftLayout;
    /**
     * 右按钮
     */
    protected LinearLayout mTitleRightLayout;
    /**
     * 标题视图
     */
    public TextView titleTextView;
    /**
     *
     */
    private Activity context = this;
    private LayoutInflater inflater;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ((ControlApplication) getApplication()).addActivity(this);
        setContentView(R.layout.activity_base_title);
        initView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (titleTextView != null) {
            titleTextView.setText(title);
        }
    }

    public View getContentView() {
        return mContentView;
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        String title = getString(titleId);
        setTitle(title);
    }

    long[] mHits = new long[8];

    public void clickMore() {
        //每点击一次 实现左移一格数据
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //给数组的最后赋当前时钟值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        //当0出的值大于当前时间-500时  证明在500秒内点击了2次
        if (mHits[0] > SystemClock.uptimeMillis() - 1000) {
            mHits = new long[8];//当触发事件后，重新计时
//            Toast.makeText(this, "服务器域名：" + FusionConstant.httpServerAddr, Toast.LENGTH_SHORT).show();
        }
    }

    public void setRvTitleGone() {
        if (rvTitle != null) {
            rvTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化视图
     *
     * @Title: initView
     */
    private void initView() {
        rvTitle = (RelativeLayout) findViewById(R.id.title_layout);
        mTitleLeftLayout = (LinearLayout) findViewById(R.id.left_layout);
        mTitleRightLayout = (LinearLayout) findViewById(R.id.right_layout);
        mContentView = (LinearLayout) findViewById(R.id.content_layout);
        titleTextView = (TextView) findViewById(R.id.bar_title);
        inflater = getLayoutInflater();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContentView.addView(inflater.inflate(getContentLayout(), null), params);
    }

    /**
     * 设置左边返回按钮
     */
    protected void setLeftBack() {
        View backView = inflater.inflate(R.layout.view_bar_back, null);
        backView.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleLeftLayout.addView(backView);
    }

    /**
     * 设置左边返回按钮
     */
    protected void setLeftBack(View.OnClickListener listener) {
        View backView = inflater.inflate(R.layout.view_bar_back, null);
        backView.findViewById(R.id.back_btn).setOnClickListener(listener);
        mTitleLeftLayout.addView(backView);
    }

    public Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            dismissProgressDialog();
            switch (msg.arg2) {
                default: {
                    BaseTitleActivity.this.handleMessage(msg);
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };


    public abstract void handleMessage(Message msg);
}
