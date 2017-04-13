package com.fzx.framework.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * 基础标题界面
 *
 * @author fzx
 * @ClassName: BaseTitleFragment
 * @date 2015年7月4日 上午11:18:40
 */
public abstract class BaseTitleFragment extends BaseFragment {
    private RelativeLayout llTitle;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_base_title, container, false);
        initView(view);
        return view;
    }

    /**
     * 设置标题
     *
     * @param title
     * @Title: setTitle
     */
    public void setTitle(CharSequence title) {
        if (titleTextView != null) {
            titleTextView.setText(title);
        }
    }

    /**
     * 隐藏标题栏
     */
    public void setTitleGone() {
        llTitle.setVisibility(View.GONE);
    }

    /**
     * 设置标题
     *
     * @param titleId
     * @Title: setTitle
     */
    public void setTitle(int titleId) {
        if (titleTextView != null) {
            titleTextView.setText(titleId);
        }
    }

    /**
     * 初始化视图
     *
     * @Title: initView
     */
    private void initView(View view) {
        mTitleLeftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
        mTitleRightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
        llTitle = (RelativeLayout) view.findViewById(R.id.title_layout);
        mContentView = (LinearLayout) view.findViewById(R.id.content_layout);
        titleTextView = (TextView) view.findViewById(R.id.bar_title);
        LayoutInflater inflater = this.getActivity().getLayoutInflater();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mContentView.addView(inflater.inflate(getContentLayout(), null), params);
    }

    public abstract void handleMessage(Message msg);
}
