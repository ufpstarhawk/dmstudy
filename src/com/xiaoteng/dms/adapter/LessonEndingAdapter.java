package com.xiaoteng.dms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoteng.dms.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程学习
 * @author zach
 *
 */
public class LessonEndingAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public LessonEndingAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        mData=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("lesson_name", "第" + (i + 1) + "行的标题");
            map.put("lesson_data", "5月10日完成学习");
            mData.add(map);
        }
    }

    public void addData(String lesson_name, String hit) {
        Map<String, Object> mapadd = new HashMap<String, Object>();
        mapadd.put("lesson_name", lesson_name);
        mData.add(0,mapadd);
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
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lesson_endingstudy_item, null);
            holder = new ViewHolder();
            holder.lesson_name = (TextView) convertView.findViewById(R.id.lesson_name);
            holder.lesson_data = (TextView) convertView.findViewById(R.id.lesson_data);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.lesson_name.setText(mData.get(position).get("lesson_name").toString());
        holder.lesson_data.setText(mData.get(position).get("lesson_data").toString());
        return convertView;
    }

    public static class ViewHolder {
        public TextView lesson_name;
        public TextView lesson_data;
    }
}