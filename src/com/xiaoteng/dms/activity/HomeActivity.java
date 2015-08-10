package com.xiaoteng.dms.activity;

import java.util.ArrayList;
import java.util.List;

import org.apmem.tools.layouts.FlowLayout;

import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

//import com.czh.animation.DepthPageTransformer;
//import com.czh.animation.ZoomOutPageTransformer;
import com.xiaoteng.dms.R;
import com.xiaoteng.dms.adapter.HomePagerAdapter;
import com.xiaoteng.dms.adapter.LessonEndingAdapter;
import com.xiaoteng.dms.adapter.LessonPlaystudyAdapter;
import com.xiaoteng.dms.adapter.NewestLessonAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.Window;

public class HomeActivity extends Activity {
	private ImageView search, msg, settings;
	private Button newest, popular, rating, filter;
	private long exitTime = 0;// 定义一个变量，来标识是否退出
	/*** 全部课程,正在学习,已学完 */
	private TextView tv_course, tv_playstudy, tv_endingstudy, txtGrade,
			txtSubject, txtMaterial;
	/**** 下划线 */
	private ViewPager mViewPager;// 页卡内容
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private List<String> filterGrade = new ArrayList<String>(); // 筛选页“年级”列表
	private List<String> filterSubject = new ArrayList<String>(); // 筛选页“科目”列表
	private List<String> filterMaterial = new ArrayList<String>(); // 筛选页“教材”列表
	private List<View> listViews = new ArrayList<View>();; // Tab页面列表
	private ListView list1, list2, list3;
	private View view1, view2, view3;
	private int bmpW;// 动画图片宽度
	private ImageView glissade_iv;// 下划线,
	private NewestLessonAdapter newestLessonAdapter;
	private LessonPlaystudyAdapter lessonPlaystudyAdapter;
	private LessonEndingAdapter lessonEndingAdapter;

	private int mScreen1_3;
	private ImageView mtabLine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		search = (ImageView) findViewById(R.id.search);
		msg = (ImageView) findViewById(R.id.msg);
		settings = (ImageView) findViewById(R.id.settings);
		search.setOnClickListener(onclick);
		msg.setOnClickListener(onclick);
		settings.setOnClickListener(onclick);
		InitTextView();
		HomePager();
	}

	/****
	 * 初始化头标
	 */
	private void InitTextView() {
		tv_course = (TextView) findViewById(R.id.tv_course);
		tv_playstudy = (TextView) findViewById(R.id.tv_playstudy);
		tv_endingstudy = (TextView) findViewById(R.id.tv_endingstudy);

		tv_course.setOnClickListener(new MyOnClickListener(0));
		tv_playstudy.setOnClickListener(new MyOnClickListener(1));
		tv_endingstudy.setOnClickListener(new MyOnClickListener(2));

		// 以下是设置移动条的初始位置

		mtabLine = (ImageView) findViewById(R.id.glissade_iv);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels / 3;
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mtabLine
				.getLayoutParams();
		lp.width = mScreen1_3-mScreen1_3/3;

		mtabLine.setLayoutParams(lp);
	}

	public OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search:
				Intent search = new Intent();
				search.setClass(HomeActivity.this, SearchActivity.class);
				startActivity(search);
				break;
			case R.id.msg:
				Intent msg = new Intent();
				msg.setClass(HomeActivity.this, MsgActivity.class);
				startActivity(msg);
				break;
			case R.id.settings:
				Intent settings = new Intent();
				settings.setClass(HomeActivity.this, SettingsActivity.class);
				startActivity(settings);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 头标点击监听
	 */
	private class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mViewPager.setCurrentItem(index);
		}
	}

	/*****
	 * 初始化页卡内容区 初始化ViewPager
	 */
	private void HomePager() {
		// 加载要显示的选项卡
		// 实例化一个LayoutInflater对象
		LayoutInflater mInflater = getLayoutInflater();
		mViewPager = (ViewPager) findViewById(R.id.homeViewPager);
		newest = (Button) findViewById(R.id.tv_newest);
		popular = (Button) findViewById(R.id.tv_popular);
		rating = (Button) findViewById(R.id.tv_rating);
		filter = (Button) findViewById(R.id.tv_filter);

		view1 = mInflater.inflate(R.layout.newest_lesson, null);
		list1 = (ListView) view1.findViewById(R.id.newestlessonListView);
		newestLessonAdapter = new NewestLessonAdapter(this);
		list1.setAdapter(newestLessonAdapter);
		list1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent newestlessonListView = new Intent();
				newestlessonListView.setClass(HomeActivity.this,
						FragmentDetailedActivity.class);
				startActivity(newestlessonListView);
			}
		});
		listViews.add(view1);
		view2 = mInflater.inflate(R.layout.lesson_playstudy, null);
		list2 = (ListView) view2.findViewById(R.id.lesson_playstudy_list);
		lessonPlaystudyAdapter = new LessonPlaystudyAdapter(this);
		list2.setAdapter(lessonPlaystudyAdapter);
		list2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent lesson_playstudy_list = new Intent();
				// 判断是否登陆。已登录
				lesson_playstudy_list.setClass(HomeActivity.this,
						PromptLoginActivity.class);
				startActivity(lesson_playstudy_list);
			}
		});
		listViews.add(view2);

		view3 = mInflater.inflate(R.layout.lesson_endingstudy, null);
		list3 = (ListView) view3.findViewById(R.id.ending_list);
		lessonEndingAdapter = new LessonEndingAdapter(this);
		list3.setAdapter(lessonEndingAdapter);
		list3.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent ending_list = new Intent();
				ending_list.setClass(HomeActivity.this,
						FragmentDetailedActivity.class);
				startActivity(ending_list);
			}

		});
		listViews.add(view3);
		mViewPager.setAdapter(new HomePagerAdapter(listViews));
		mViewPager.setCurrentItem(0);
//		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		// mViewPager.setPageTransformer(true, new DepthPageTransformer());
		mViewPager.setOnPageChangeListener(new HomeOnPageChangeListener());
	}

	public void onClickHome() {// 从筛选页面返回时的公共函数
		listViews.remove(0);
		LayoutInflater mInflater = getLayoutInflater();
		mViewPager = (ViewPager) findViewById(R.id.homeViewPager);
		view1 = mInflater.inflate(R.layout.newest_lesson, null);
		list1 = (ListView) view1.findViewById(R.id.newestlessonListView);
		newestLessonAdapter = new NewestLessonAdapter(this);
		list1.setAdapter(newestLessonAdapter);
		list1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent newestlessonListView = new Intent();
				newestlessonListView.setClass(HomeActivity.this,
						FragmentDetailedActivity.class);
				startActivity(newestlessonListView);
			}
		});
		listViews.add(0, view1);
		mViewPager.setAdapter(new HomePagerAdapter(listViews));
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new HomeOnPageChangeListener());
	}

	public void onClickNewest(View view) {// 点击“最新”
		newestLessonAdapter.modifyData();// 访问接口后更新数据
		newestLessonAdapter.notifyDataSetChanged();
	}

	public void onClickHomeNewest(View view) {// 从筛选页面点击“最新”
		onClickHome();
		newestLessonAdapter.modifyData();// 访问接口后更新数据
		newestLessonAdapter.notifyDataSetChanged();
	}

	public void onClickPopular(View view) {// 点击“热门”
		newestLessonAdapter.modifyData();
		newestLessonAdapter.notifyDataSetChanged();
	}

	public void onClickHomePopular(View view) {// 从筛选页面点击“热门”
		onClickHome();
		newestLessonAdapter.modifyData();// 访问接口后更新数据
		newestLessonAdapter.notifyDataSetChanged();
	}

	public void onClickReview(View view) {// 点击“评价”
		newestLessonAdapter.modifyData();
		newestLessonAdapter.notifyDataSetChanged();
	}

	public void onClickHomeReview(View view) {// 从筛选页面点击“评价”
		onClickHome();
		newestLessonAdapter.modifyData();// 访问接口后更新数据
		newestLessonAdapter.notifyDataSetChanged();
	}

	public void onClickFilter(View view) {// 点击“筛选”
		listViews.remove(0);// 删除原页卡
		LayoutInflater mInflater = getLayoutInflater();
		mViewPager = (ViewPager) findViewById(R.id.homeViewPager);
		view1 = mInflater.inflate(R.layout.newest_lesson_filter, null);
		listViews.add(0, view1);// 在原页卡位置加入新页卡
		mViewPager.setAdapter(new HomePagerAdapter(listViews));
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new HomeOnPageChangeListener());
		AddFilterButtons(this);
	}

	public class HomeOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			resetTextView();
			switch (position) {
			case 0:
				tv_course.setTextColor(Color.parseColor("#008000"));
				break;

			case 1:
				tv_playstudy.setTextColor(Color.parseColor("#008000"));
				break;
			case 2:
				tv_endingstudy.setTextColor(Color.parseColor("#008000"));
				break;
			}

		}

		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPx) {
			// 0-1:position:0 positionOffset:0->1
			// positionOffsetPx:0->1*屏幕的宽度
			// 设置marginLeft：position*(mSreen/3)+positionOffset*(mSreen/3)
			LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mtabLine
					.getLayoutParams();
			lp.leftMargin = (int) (position * mScreen1_3 + positionOffset
					* mScreen1_3+mScreen1_3/6);
			mtabLine.setLayoutParams(lp);
		}
	}

	/***
	 * 添加筛选内容
	 */
	public void AddFilterButtons(Context context) {
		filterGrade.clear();
		// 以下数据需改成通过接口获得
		filterGrade.add("学前");
		filterGrade.add("一年级");
		filterGrade.add("二年级");
		filterGrade.add("三年级");
		filterGrade.add("四年级");
		filterGrade.add("五年级");
		filterGrade.add("六年级");
		filterGrade.add("小升初");
		for (String i : filterGrade) {
			Button grade = new Button(context);
			grade.setText(i);
			grade.setBackground(getResources().getDrawable(
					R.drawable.btn_filter_bg));
			FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(
					FlowLayout.LayoutParams.WRAP_CONTENT,
					FlowLayout.LayoutParams.WRAP_CONTENT);
			int width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 85, getResources()
							.getDisplayMetrics());
			layoutParams.width = width;
			int height = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 45, getResources()
							.getDisplayMetrics());
			layoutParams.height = height;
			int margin = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 5, getResources()
							.getDisplayMetrics());
			layoutParams.bottomMargin = margin;
			layoutParams.topMargin = margin;
			layoutParams.leftMargin = margin;
			layoutParams.rightMargin = margin;
			grade.setLayoutParams(layoutParams);
			grade.setId(i.indexOf(i, 0));
			grade.setOnClickListener(new gradeFilterListener(grade));
			org.apmem.tools.layouts.FlowLayout gradeFilter = new org.apmem.tools.layouts.FlowLayout(context);
			gradeFilter = (org.apmem.tools.layouts.FlowLayout) view1
					.findViewById(R.id.gradeFilter);
			gradeFilter.addView(grade);
		}
		filterSubject.clear();
		// 以下数据需改成通过接口获得
		filterSubject.add("数学");
		filterSubject.add("语文");
		filterSubject.add("英语");
		filterSubject.add("作文");
		filterSubject.add("特长");
		filterSubject.add("择校");
		filterSubject.add("早教");
		filterSubject.add("幼儿园");
		for (String i : filterSubject) {
			Button subject = new Button(context);
			subject.setText(i);
			subject.setBackground(getResources().getDrawable(
					R.drawable.btn_filter_bg));
			FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(
					FlowLayout.LayoutParams.WRAP_CONTENT,
					FlowLayout.LayoutParams.WRAP_CONTENT);
			int width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 85, getResources()
							.getDisplayMetrics());
			layoutParams.width = width;
			int height = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 45, getResources()
							.getDisplayMetrics());
			layoutParams.height = height;
			int margin = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 5, getResources()
							.getDisplayMetrics());
			layoutParams.bottomMargin = margin;
			layoutParams.topMargin = margin;
			layoutParams.leftMargin = margin;
			layoutParams.rightMargin = margin;
			subject.setLayoutParams(layoutParams);
			subject.setId(i.indexOf(i, 0));
			subject.setOnClickListener(new subjectFilterListener(subject));
			org.apmem.tools.layouts.FlowLayout subjectFilter = new org.apmem.tools.layouts.FlowLayout(context);
			subjectFilter = (org.apmem.tools.layouts.FlowLayout) view1
					.findViewById(R.id.subjectFilter);
			subjectFilter.addView(subject);
		}
		filterMaterial.clear();
		// 以下数据需改成通过接口获得
		filterMaterial.add("通用版");
		filterMaterial.add("人教版");
		filterMaterial.add("北师大版");
		filterMaterial.add("华师大版");
		filterMaterial.add("沪科版");
		filterMaterial.add("苏科版");
		filterMaterial.add("冀教版");
		filterMaterial.add("鲁教版");
		for (String i : filterMaterial) {
			Button material = new Button(context);
			material.setText(i);
			material.setBackground(getResources().getDrawable(
					R.drawable.btn_filter_bg));
			FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(
					FlowLayout.LayoutParams.WRAP_CONTENT,
					FlowLayout.LayoutParams.WRAP_CONTENT);
			int width = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 85, getResources()
							.getDisplayMetrics());
			layoutParams.width = width;
			int height = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 45, getResources()
							.getDisplayMetrics());
			layoutParams.height = height;
			int margin = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 5, getResources()
							.getDisplayMetrics());
			layoutParams.bottomMargin = margin;
			layoutParams.topMargin = margin;
			layoutParams.leftMargin = margin;
			layoutParams.rightMargin = margin;
			material.setLayoutParams(layoutParams);
			material.setId(i.indexOf(i, 0));
			material.setOnClickListener(new materialFilterListener(material));
			org.apmem.tools.layouts.FlowLayout materialFilter = new org.apmem.tools.layouts.FlowLayout(
					context);
			materialFilter = (org.apmem.tools.layouts.FlowLayout) view1
					.findViewById(R.id.materialFilter);
			materialFilter.addView(material);
		}
	}

	public void resetTextView() {
		tv_course.setTextColor(Color.BLACK);
		tv_playstudy.setTextColor(Color.BLACK);
		tv_endingstudy.setTextColor(Color.BLACK);

	}

	private class gradeFilterListener implements OnClickListener {
		private Button btn;

		private gradeFilterListener(Button btn) {
			this.btn = btn;
		}

		public void onClick(View v) {
			btn.setBackground(getResources().getDrawable(
					R.drawable.btn_filter_bg_down));
			txtGrade = (TextView) findViewById(R.id.txtGrade);
			txtGrade.setText(txtGrade.getText() + btn.getText().toString()
					+ " ");
		}
	}

	private class subjectFilterListener implements OnClickListener {
		private Button btn;

		private subjectFilterListener(Button btn) {
			this.btn = btn;
		}

		public void onClick(View v) {
			btn.setBackground(getResources().getDrawable(
					R.drawable.btn_filter_bg_down));
			txtSubject = (TextView) findViewById(R.id.txtSubject);
			txtSubject.setText(txtSubject.getText() + btn.getText().toString()
					+ " ");
		}
	}

	private class materialFilterListener implements OnClickListener {
		private Button btn;

		private materialFilterListener(Button btn) {
			this.btn = btn;
		}

		public void onClick(View v) {
			btn.setBackground(getResources().getDrawable(
					R.drawable.btn_filter_bg_down));
			txtMaterial = (TextView) findViewById(R.id.txtMaterial);
			txtMaterial.setText(txtMaterial.getText()
					+ btn.getText().toString() + " ");
		}
	}

	// 双击退出
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		// 通过记录按键时间计算时间差双击退出
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}

}