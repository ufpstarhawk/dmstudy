package com.xiaoteng.dms.activity.fragment;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaoteng.dms.R;

public class FragmentDetailed1 extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Map<String, Object>> mData;
	private int chapterItem = 3;

	public FragmentDetailed1(Context context) {
		mInflater = LayoutInflater.from(context);
		init();
	}

	private void init() {
		mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 30; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("titel_text", "第" + (i + 1) + "行的标题");
			map.put("position", i + 1);
			mData.add(map);
		}
	}

	public void addData(String titel_text, String title_lesson_classify,
			String title_lesson) {
		Map<String, Object> mapadd = new HashMap<String, Object>();
		mapadd.put("titel_text", titel_text);
		mData.add(0, mapadd);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int s = Integer
				.parseInt(mData.get(position).get("position").toString());

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fragment_item_detailed1,
					null);
			holder = new ViewHolder();
			holder.titel_text = (TextView) convertView
					.findViewById(R.id.titel_text);
			holder.nameate_thead = (ImageView) convertView
					.findViewById(R.id.thread_top);
			holder.nameplate = (ImageView) convertView
					.findViewById(R.id.nameplate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		TextPaint tv_paint = holder.titel_text.getPaint();
		if (s == 1) {
			holder.nameate_thead.setVisibility(View.INVISIBLE);
			holder.nameplate.setVisibility(View.INVISIBLE);

			holder.titel_text.setText(mData.get(position).get("titel_text")
					.toString());

			tv_paint.setFakeBoldText(true);
			holder.titel_text.setTextSize(18);
			holder.titel_text.setTextColor(Color.BLACK);
		}else if (s == 30) {
			holder.nameate_thead.setVisibility(View.INVISIBLE);
			holder.titel_text.setText(mData.get(position).get("titel_text")
					.toString());
		}else {
			holder.nameate_thead.setVisibility(View.VISIBLE);
			holder.nameplate.setVisibility(View.VISIBLE);
			holder.titel_text.setText(mData.get(position).get("titel_text")
					.toString());
			holder.titel_text.setTextSize(15);
			tv_paint.setFakeBoldText(false);
			holder.titel_text.setTextColor(Color.DKGRAY);
		}
		
//		Log.i("info", mData.get(position).get("titel_text").toString());
		return convertView;
	}

	public static class ViewHolder {
		public TextView titel_text;
		public ImageView nameplate;
		public ImageView nameate_thead;
	}
}