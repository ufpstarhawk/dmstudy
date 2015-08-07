package com.xiaoteng.dms.adapter;

import java.util.ArrayList;

import com.xiaoteng.dms.data.LessonData;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListItemAdapter extends BaseAdapter{
	 private ArrayList<LessonData> lissonList = null; 
	 private Context context = null; 

	 /** 
      * 构造函数,初始化Adapter,将数据传入 
      * @param bookshelfList 
      * @param context 
      */ 
     public ListItemAdapter(ArrayList<LessonData> lissonList, Context context) {
         this.lissonList = lissonList;
         this.context = context;
     }
     
     /**
      * 获取列表总行
      */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lissonList == null ? 0 : lissonList.size();
	}
	/**
	 * 获取当前选中的图片
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lissonList == null ? null : lissonList.get(position);
	}
	/**
	 * 获取当前的位置
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	/***
	 * 得到每行视图
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
}
