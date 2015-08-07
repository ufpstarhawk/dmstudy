package com.xiaoteng.dms.activity;
import com.xiaoteng.dms.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;


public  class Ratingbar extends Activity implements OnRatingBarChangeListener{
	private RatingBar rating_bar;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.ratingbar_room);
			rating_bar = (RatingBar) this.findViewById(R.id.room_ratingbar);
			rating_bar.setMax(5);//设置刻度
			rating_bar.setProgress(1);//设置当前刻度
			rating_bar.setOnRatingBarChangeListener(this);
		}
		@Override
		public void onRatingChanged(RatingBar ratingBar,
				float rating, boolean fromUser) {
			// TODO Auto-generated method stub
			int progress = ratingBar.getProgress();
			Toast.makeText(Ratingbar.this, "progress:"+progress+"rating:"+rating, 1).show();
		}
}
