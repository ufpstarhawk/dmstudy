package com.xiaoteng.dms.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.xiaoteng.dms.R;

public class OpenDialog extends Dialog {

    public OpenDialog(Context context) {
        super(context);
    }

    public OpenDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class OpenDialogBuilder {
        private Context context;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public OpenDialogBuilder(Context context) {
            this.context = context;
        }

        public OpenDialogBuilder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public OpenDialog create(int a) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final OpenDialog dialog = new OpenDialog(context);
            View layout = inflater.inflate(R.layout.dialog_open, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            dialog.setTitle("验证手机号");
            return dialog;
        }
    }
}