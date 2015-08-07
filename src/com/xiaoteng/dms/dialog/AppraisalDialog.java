package com.xiaoteng.dms.dialog;
/**
 * 课程评价
 */
import com.xiaoteng.dms.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class AppraisalDialog extends Dialog{

	public AppraisalDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public AppraisalDialog(Context context, int theme) {
        super(context, theme);
    }
	public static class AppDialogBuilder {
        private Context context;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public AppDialogBuilder(Context context) {
            this.context = context;
        }

        public AppDialogBuilder setContentView(View v) {
            this.contentView = v;
            return this;
        }
        

        public AppraisalDialog create(int a) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final AppraisalDialog dialog = new AppraisalDialog(context);
            View layout = inflater.inflate(R.layout.dialong_appraisal, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            return dialog;
        }
	}
}
