package com.libra.mvvm.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.libra.mvvm.R;
import com.wang.avi.AVLoadingIndicatorView;


public class CustomProgressDialog extends Dialog {
    private View mDialogView;
    private boolean cancelTouchOutside;

    public CustomProgressDialog(Builder builder) {
        super(builder.context);
        mDialogView = builder.mDialogView;
        cancelTouchOutside = builder.cancelTouchOutside;
    }

    private CustomProgressDialog(Builder builder, int themeResId) {
        super(builder.context, themeResId);
        mDialogView = builder.mDialogView;
        cancelTouchOutside = builder.cancelTouchOutside;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mDialogView);
        setCanceledOnTouchOutside(cancelTouchOutside);
    }


    public static final class Builder {
        Context context;
        private int resStyle = -1;
        private View mDialogView;
        private boolean cancelTouchOutside;
        private AVLoadingIndicatorView loadingIndicatorView;

        public Builder(Context context) {
            this.context = context;
            mDialogView = LayoutInflater.from(context).inflate(R.layout.mvvm_dialog_progress, null);
            loadingIndicatorView = mDialogView.findViewById(R.id.v_loading);
        }

        /**
         * 设置主题
         *
         * @param resStyle style id
         * @return CustomProgressDialog.Builder
         */
        public Builder setTheme(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        /**
         * 设置点击dialog外部是否取消dialog
         *
         * @param val 点击外部是否取消dialog
         * @return
         */
        public Builder cancelTouchOutside(boolean val) {
            cancelTouchOutside = val;
            return this;
        }

        public CustomProgressDialog build() {
            if (resStyle != -1) {
                return new CustomProgressDialog(this, resStyle);
            } else {
                return new CustomProgressDialog(this);
            }
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();

    }
}
