package com.fzx.framework.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 基础界面
 *
 * @ClassName: BaseFragment
 * @author fzx
 * @date 2015年7月4日 上午11:14:36
 *
 */
public abstract class BaseFragment extends Fragment {

	/**
	 * 设备ID
	 */
	private String mDeviceID = "";

	/**
	 * 数据修改监听事件
	 */
	protected OnPushDataChangedListener mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (OnPushDataChangedListener) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(getContentLayout(), container, false);
		return view;
	}

	/**
	 * 获取布局文件
	 *
	 * @return 布局文件ID
	 */
	protected abstract int getContentLayout();

	/**
	 * 获取设备ID
	 *
	 * @Title: getDeviceID
	 * @return
	 */
	protected String getDeviceID() {
		return mDeviceID;
	}

	/**
	 * 获取Activity 监听
	 *
	 * @Title: getActivityListener
	 * @return
	 */
	protected OnPushDataChangedListener getActivityListener() {
		return mListener;
	}

}
