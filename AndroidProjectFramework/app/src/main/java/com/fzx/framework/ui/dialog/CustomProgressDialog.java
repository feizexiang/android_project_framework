package com.fzx.framework.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * 进度条对话框
 *
 * @ClassName: CustomProgressDialog
 * @author fzx
 * @date 2015年6月12日 下午8:48:47
 *
 */
public class CustomProgressDialog extends Dialog {
	/**
	 * [构造简要说明]
	 *
	 * @param context
	 *            上下文
	 */
	public CustomProgressDialog(Context context) {
		super(context, R.style.dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * [构造简要说明]
	 *
	 * @param context
	 *            上下文
	 * @param theme
	 *            主题
	 */
	public CustomProgressDialog(Context context, int theme) {
		super(context, R.style.dialog);
	}

	/**
	 *
	 * [对话框的Builder]<BR>
	 * [功能详细描述]
	 *
	 * @author Administrator
	 * @version [RCS Client R002C04L4ZT01, 2013-5-17]
	 */
	public static class Builder {
		// 上下文
		private Context context;

		// dialog title
		private String title;

		// dialog message
		private String message;
		/**
		 * 可以关闭
		 */
		private boolean mCancelable = true;

		/**
		 *
		 * [构造简要说明]
		 *
		 * @param context
		 *            上下文
		 */
		public Builder(Context context) {
			this.context = context;
		}

		/**
		 * 设置是否可以关闭
		 *
		 * @Title: setCancelable
		 * @param flag
		 */
		public Builder setCancelable(boolean flag) {
			mCancelable = flag;
			return this;
		}

		/**
		 * Set the Dialog message from String
		 *
		 * @param parammessage
		 *            提示信息
		 * @return Builder
		 */
		public Builder setMessage(String parammessage) {
			this.message = parammessage;
			return this;
		}

		/**
		 * Set the Dialog title from String
		 *
		 * @param paramtitle
		 *            标题
		 * @return Builder
		 */
		public Builder setTitle(String paramtitle) {
			this.title = paramtitle;
			return this;
		}

		/**
		 * Create the custom dialog
		 *
		 * @return CustomDialog
		 */
		public CustomProgressDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final CustomProgressDialog dialog = new CustomProgressDialog(
					context);

			View layout = inflater.inflate(R.layout.dialog_progress_custom,
					null);
			if (!mCancelable) {
				dialog.setCancelable(mCancelable);
			}

			dialog.setContentView(layout);

			TextView titleView = (TextView) layout
					.findViewById(R.id.dialog_alert_title);
			titleView.setText(title);
			TextView loading = (TextView) layout.findViewById(R.id.loading_txt);
			loading.setText(message != null ? message : "");

			ImageView imageView = (ImageView) layout
					.findViewById(R.id.loading_img);
			AnimationDrawable animationDrawable = (AnimationDrawable) imageView
					.getBackground();
			animationDrawable.start();

			dialog.setContentView(layout);
			return dialog;
		}
	}
}
