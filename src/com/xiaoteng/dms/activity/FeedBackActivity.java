package com.xiaoteng.dms.activity;
/***
 * 意见反馈
 */
import android.text.Editable;
import android.text.TextWatcher;

import com.xiaoteng.dms.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedBackActivity extends Activity{
	private ImageView backing_bg;
	private Button btnSubmitFeedback;
	private EditText textFeedback;
	private TextView wordsLeft;
	private int MAX_LENGTH=240, leftwords=MAX_LENGTH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);

		backing_bg = (ImageView) findViewById(R.id.backing_bg);
		backing_bg.setOnClickListener(onClickBack);
		btnSubmitFeedback = (Button) findViewById(R.id.btnSubmitFeedback);
		textFeedback = (EditText) findViewById(R.id.textFeedback);
		wordsLeft = (TextView) findViewById(R.id.wordsLeft);
		wordsLeft.setText("您还可以输入 " + 500 + " 个字");
		//btnSubmitFeedback.setOnClickListener(onclick);
		textFeedback.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(leftwords > 0){
					leftwords = MAX_LENGTH - textFeedback.getText().length();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				wordsLeft.setText("您还可以输入 "+leftwords+" 个字");
			}

			@Override
			public void afterTextChanged(Editable s) {
				wordsLeft.setText("您还可以输入 "+leftwords+" 个字");
			}
		});
	}

	public OnClickListener onClickBack = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent newintent = new Intent();
			newintent.setClass(FeedBackActivity.this, SettingsActivity.class);
			startActivity(newintent);
			FeedBackActivity.this.finish();
		}
	};
}
