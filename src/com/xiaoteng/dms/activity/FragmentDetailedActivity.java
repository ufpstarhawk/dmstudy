package com.xiaoteng.dms.activity;

import java.util.ArrayList;
import java.util.List;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.activity.fragment.FragmentDetailed1;
import com.xiaoteng.dms.activity.fragment.FragmentDetailed2;
import com.xiaoteng.dms.activity.fragment.FragmentDetailed3;
import com.xiaoteng.dms.adapter.HomePagerAdapter;

import android.widget.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.Window;
/**
 * 视频观看
 * @author zach
 *
 */
public class FragmentDetailedActivity extends Activity implements OnClickListener{
	private ImageView search,msg,settings;
	private ListView lesson_playstudy_list,ending_list;
	
	private long exitTime = 0;// 定义一个变量，来标识是否退出
	/***全部课程,正在学习,已学完*/
	private TextView tv_course,tv_playstudy,tv_endingstudy;
	/****下划线*/
	private ViewPager mViewPager;//页卡内容
	private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private List<View> listViews; // Tab页面列表
	private ListView list1,list2, list3;
	private View view1, view2, view3;
    private int bmpW;// 动画图片宽度
    private ImageView glissade_iv;//下划线
	private FragmentDetailed1 newestLessonAdapter;
	private FragmentDetailed2 lessonPlaystudyAdapter;
	private FragmentDetailed3 lessonEndingAdapter;
	
	private SurfaceView sv_video;
	//播放栏/标题栏
	private LinearLayout linea_player,linea_title;
	//播放/暂停/横屏全屏
	private ImageView bt_play,bt_pause,bt_spread;
	private boolean player = false;
	private Button bt_grade;
	private View dialog_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_play_detailed_viewpager);
		
		InitTextView();
		HomePager();
		
	}
	
	
//	/***
//	 * 视频横竖屏设置
//	 */
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig); 
//		// 检测屏幕的方向：纵向或横向 
//				if (this.getResources().getConfiguration().orientation  
//						== Configuration.ORIENTATION_LANDSCAPE) { 
//					//当前为横屏， 在此处添加额外的处理代码 
//				} 
//				else if (this.getResources().getConfiguration().orientation  
//						== Configuration.ORIENTATION_PORTRAIT) { 
//					//当前为竖屏， 在此处添加额外的处理代码 
//				} 
//
//	}
	
	/****
	 * 初始化头标
	 */
	private void InitTextView() {
		tv_course = (TextView) findViewById(R.id.tv_course);
		tv_playstudy = (TextView) findViewById(R.id.tv_playstudy);
		tv_endingstudy = (TextView) findViewById(R.id.tv_endingstudy);
		
		
		sv_video = (SurfaceView) findViewById(R.id.sv_video);
		linea_player = (LinearLayout) findViewById(R.id.linea_player);
		linea_title = (LinearLayout) findViewById(R.id.linea_title);
		
	//	bt_pause = (ImageView) findViewById(R.id.bt_pause);
		bt_play = (ImageView) findViewById(R.id.bt_play);
		bt_spread = (ImageView) findViewById(R.id.bt_spread);
		
		
		tv_course.setOnClickListener(new MyOnClickListener(0));
		tv_playstudy.setOnClickListener(new MyOnClickListener(1));
		tv_endingstudy.setOnClickListener(new MyOnClickListener(2));
		
		//以下是设置移动条的初始位置
		glissade_iv = (ImageView) findViewById(R.id.glissade_iv);
		bmpW = BitmapFactory.decodeResource(getResources(), 
				R.drawable.glissade1).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		glissade_iv.setImageMatrix(matrix);// 设置动画初始位置
		
//		bt_pause.setOnClickListener(this);
		bt_play.setOnClickListener(this);
		bt_spread.setOnClickListener(this);
		sv_video.setOnClickListener(this);
	}
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
	 * 初始化页卡内容区
	 * 初始化ViewPager
	 */
	private void HomePager(){
		//加载要显示的选项卡
		//实例化一个LayoutInflater对象
		LayoutInflater mInflater = getLayoutInflater();
		mViewPager = (ViewPager)findViewById(R.id.homeViewPager);

		listViews = new ArrayList<View>();
		view1 = mInflater.inflate(R.layout.fragment_play_detailed1, null);
		list1 = (ListView) view1.findViewById(R.id.list_detaile);
		newestLessonAdapter = new FragmentDetailed1(this);
		list1.setAdapter(newestLessonAdapter);
		listViews.add(view1);

		view2 = mInflater.inflate(R.layout.fragment_play_detailed2, null);
		list2 = (ListView) view2.findViewById(R.id.list_detailed2);
		lessonPlaystudyAdapter = new FragmentDetailed2(this);
		list2.setAdapter(lessonPlaystudyAdapter);
		listViews.add(view2);
		
		view3 = mInflater.inflate(R.layout.fragment_play_detailed3, null);
		// grade = (RatingBar) view3.findViewById(R.id.ratingbar_score);
		bt_grade = (Button) view3.findViewById(R.id.bt_grade);
		bt_grade.setOnClickListener(this);
		list3 = (ListView) view3.findViewById(R.id.list_detaile);
		lessonEndingAdapter = new FragmentDetailed3(this);
		list3.setAdapter(lessonEndingAdapter);
		listViews.add(view3);
		
		//mViewPager = (ViewPager) findViewById(R.id.homeViewPager);
		mViewPager.setAdapter(new HomePagerAdapter(listViews));
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new HomeOnPageChangeListener());
		
	}
	
	
	public class HomeOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int i) {
			Animation animation = new TranslateAnimation(one*currIndex, one*i, 0, 0);
			currIndex = i;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			glissade_iv.startAnimation(animation);
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sv_video:
			if (linea_player.getVisibility() == View.GONE
			){
				linea_player.setVisibility(View.VISIBLE);
				linea_title.setVisibility(View.VISIBLE);
				//上下栏显示3秒后自动隐藏
//				Thread thread = new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						try {
//							Thread.sleep(2000);
//							if(linea_player.getVisibility() == View.VISIBLE){
//								linea_player.setVisibility(View.GONE);
//								linea_title.setVisibility(View.GONE);  
//							}
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				});1
//				thread.start();
				}else if(linea_player.getVisibility() == View.VISIBLE){
				linea_player.setVisibility(View.GONE);
				linea_title.setVisibility(View.GONE);  
			}
			break;
			//播放
		case R.id.bt_play:
			if(player){
				bt_play.setImageResource(R.drawable.player_but);
				player = false;
			}
			else{
				bt_play.setImageResource(R.drawable.suspend_but);
				player = true;
			}
			
			break;
			//横屏
		case R.id.bt_return:
			finish();
			break;
		case R.id.bt_grade:
			Builder builder = new Builder(this);
			builder.setTitle("请您打分");
			LayoutInflater inflater = LayoutInflater.from(this);
			dialog_view = inflater.inflate(R.layout.dialong_appraisal, null);
			builder.setView(dialog_view);
			builder.setNegativeButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							RatingBar rb = (RatingBar) dialog_view
									.findViewById(R.id.room_ratingbar);
							float rating = rb.getRating();
							View list_item = list3.getChildAt(0);
							RatingBar rb2 = (RatingBar) list_item
									.findViewById(R.id.lesson_score);
							rb2.setRating(rating);
							// grade.setRating(rating);
							// dialog.dismiss();

						}
					});
			builder.create().show();
			break;

		default:
			break;
		}
	}
	
	
}