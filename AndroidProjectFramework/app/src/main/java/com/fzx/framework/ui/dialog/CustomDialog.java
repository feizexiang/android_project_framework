package com.fzx.framework.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;


public class CustomDialog extends Dialog implements DialogInterface {

    /**
     * [构造简要说明]
     *
     * @param context 上下文
     */
    public CustomDialog(Context context) {
        super(context, R.style.dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * [构造简要说明]
     *
     * @param context 上下文
     * @param theme   主题
     */
    public CustomDialog(Context context, int theme) {
        super(context, R.style.dialog);
    }

    /**
     * [对话框的Builder]<BR>
     * [功能详细描述]
     *
     * @author Administrator
     * @version [RCS Client R002C04L4ZT01, 2013-5-17]
     */
    public static class Builder {
        // 上下文
        private Context context;

        // 内容view
        private View contentView;

        // 确认按钮文本
        private String positiveButtonText;

        // 取消按钮文本
        private String negativeButtonText;

        // dialog title
        private String title;

        // dialog message
        private String message;
        /**
         * 是否可以关闭
         */
        private boolean cancelFlag = false;
        // 按钮的监听事件
        private DialogInterface.OnClickListener positiveButtonClickListener,
                negativeButtonClickListener;

        /**
         * [构造简要说明]
         *
         * @param context 上下文
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * [初始化内容view]<BR>
         * [功能详细描述]
         *
         * @param v 视图
         * @return Builder
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the Dialog message from String
         *
         * @param parammessage 提示信息
         * @return Builder
         */
        public Builder setMessage(String parammessage) {
            this.message = parammessage;
            return this;
        }

        public Builder setMessage(int paramtitleID) {
            this.message = this.context.getString(paramtitleID);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param paramtitle 标题
         * @return Builder
         */
        public Builder setTitle(String paramtitle) {
            this.title = paramtitle;
            return this;
        }

        /**
         * 标题
         *
         * @param paramtitleID
         * @return
         * @Title: setTitle
         */
        public Builder setTitle(int paramtitleID) {
            this.title = this.context.getString(paramtitleID);
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param paramnegativeButtonText 取消按钮文字
         * @param listener                取消按钮点击监听
         * @return Builder
         */
        public Builder setNegativeButton(String paramnegativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = paramnegativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setEnable(boolean flag) {

            return this;
        }

        /**
         * 设置可关闭
         *
         * @param flag
         * @return
         * @Title: setCancelable
         */
        public Builder setCancelable(boolean flag) {
            cancelFlag = flag;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param paramnegativeButtonRes
         * @param listener
         * @return
         * @Title: setNegativeButton
         */
        public Builder setNegativeButton(int paramnegativeButtonRes,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = this.context
                    .getString(paramnegativeButtonRes);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param parampositiveButtonText 确定按钮问题
         * @param listener                确定按钮点击监听
         * @return Builder
         */
        public Builder setPositiveButton(String parampositiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = parampositiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(int parampositiveButtonRes, DialogInterface.OnClickListener listener) {
            if (context != null) {
                this.positiveButtonText = context.getString(parampositiveButtonRes);
            }
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Create the custom dialog
         *
         * @return CustomDialog
         */
        public CustomDialog create() {
            if (context != null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final CustomDialog dialog = new CustomDialog(context);

                View layout = inflater.inflate(R.layout.dialog_custom, null);

                dialog.setContentView(layout);

                TextView titleView = (TextView) layout
                        .findViewById(R.id.dialog_alert_title);
                TextView msgView = (TextView) layout
                        .findViewById(R.id.dialog_alert_message);
                titleView.setText(title);
                Button posButton = (Button) layout
                        .findViewById(R.id.btn_alert_dialog_confirm);
                Button navButton = (Button) layout
                        .findViewById(R.id.btn_alert_dialog_cancel);
                if (positiveButtonText != null) {
                    posButton.setText(positiveButtonText);
                    if (positiveButtonClickListener != null) {
                        posButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                            }
                        });
                    }
                } else {
                    // if no confirm button just set the visibility to GONE
                    posButton.setVisibility(View.GONE);
                    layout.findViewById(R.id.btn_split_line).setVisibility(
                            View.GONE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                    lp.gravity = Gravity.CENTER;
                    navButton.setLayoutParams(lp);
                    navButton.setGravity(Gravity.CENTER);
                    navButton.setBackgroundResource(R.drawable.dialog_all_btn);
                }

                // set the cancel button
                if (negativeButtonText != null) {
                    navButton.setText(negativeButtonText);
                    if (negativeButtonClickListener != null) {
                        navButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                negativeButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
                    } else {
                        // if no confirm button just set the visibility to GONE
                        navButton.setVisibility(View.GONE);
                    }
                } else {
                    // 如果只设置了确认，没设置取消，则将确认按钮居中并且隐藏取消按钮的显示
                    navButton.setVisibility(View.GONE);
                    layout.findViewById(R.id.btn_split_line).setVisibility(
                            View.GONE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                    lp.gravity = Gravity.CENTER;
                    posButton.setLayoutParams(lp);
                    posButton.setGravity(Gravity.CENTER);
                    posButton.setBackgroundResource(R.drawable.dialog_all_btn);
                }
                if (negativeButtonText == null && positiveButtonText == null) {
                    layout.findViewById(R.id.split_line_second).setVisibility(
                            View.GONE);
                    layout.findViewById(R.id.dialog_scroll__content)
                            .setBackgroundResource(R.drawable.dialog_no_btn_bg);
                }
                // set the content message
                if (message != null) {
                    msgView.setText(message);
                } else if (contentView != null) {
                    LinearLayout contentContainer = (LinearLayout) layout
                            .findViewById(R.id.dialog_content);
                    contentContainer.removeAllViews();
                    contentContainer.addView(contentView);
                }
                dialog.setCancelable(cancelFlag);
                dialog.setContentView(layout);
                return dialog;
            }
            return null;
        }
    }
}
