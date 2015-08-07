package com.xiaoteng.dms.adapter;

import java.util.List;

import com.xiaoteng.dms.R;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class HomePagerAdapter extends PagerAdapter{
	private List<View> mListViews;

	public HomePagerAdapter() {

	}

	public HomePagerAdapter(List<View> mListViews) {
		this.mListViews = mListViews;
	}

	public void destroyItem(View arg0, int arg1, Object arg2){

		((ViewPager) arg0).removeView(mListViews.get(arg1));

	}

	public void finishUpdate(View arg0) {
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListViews.size();
	}

	 public Object instantiateItem(View arg0, int arg1) {
		 ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
		 return mListViews.get(arg1);
	 }


	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == (arg1);
	}

	public void restoreState(Parcelable arg0, ClassLoader arg1){

	}

	public Parcelable saveState() {
		return null;
	}
	public void startUpdate(View arg0) {
    }

}

