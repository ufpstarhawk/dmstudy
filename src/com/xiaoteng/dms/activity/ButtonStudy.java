package com.xiaoteng.dms.activity;

import com.xiaoteng.dms.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ButtonStudy extends Activity{
	private TextView confirm_buy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_study_lesson);
		
		confirm_buy = (TextView) findViewById(R.id.confirm_buy);
		confirm_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ButtonStudy.this,PromptLoginActivity.class);
				startActivity(intent);
			}
		});
	}
}
