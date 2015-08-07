package com.xiaoteng.dms.pager;

import com.xiaoteng.dms.R;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;

/****
 * 切换监听
 * 
 * @author zach
 * 
 */
public class HomeOnPageChangeListener implements OnPageChangeListener {
	private ImageView glissade_iv;// 下划线
	private int currIndex = 0;// 当前页卡编号
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度
	private ImageView glissade1;// 动画图片下划线
	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
	int two = one * 2;// 页卡1 -> 页卡3 偏移量

	@Override
	public void onPageSelected(int arg0) {
		Animation animation = null;
		switch (arg0) {
		case 0:
			if (currIndex == 1) {
				animation = new TranslateAnimation(one, 0, 0, 0);
			} else if (currIndex == 2) {
				animation = new TranslateAnimation(two, 0, 0, 0);
			}
			break;
		case 1:
			if (currIndex == 0) {
				animation = new TranslateAnimation(offset, one, 0, 0);
			} else if (currIndex == 2) {
				animation = new TranslateAnimation(two, one, 0, 0);
			}
			break;
		case 2:
			if (currIndex == 0) {
				animation = new TranslateAnimation(offset, two, 0, 0);
			} else if (currIndex == 1) {
				animation = new TranslateAnimation(one, two, 0, 0);
			}
			break;
		}
		currIndex = arg0;
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		glissade1.startAnimation(animation);

	}

	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
