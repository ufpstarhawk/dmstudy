package com.xiaoteng.dms.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.uitl.Codes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonPlaystudyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;
    private String url = Codes.URL_ROOT + "/lesson/index.api";

    public LessonPlaystudyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        mData=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("lesson_name", "第" + (i + 1) + "行的标题");
            mData.add(map);
        }

        //SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        //String longhistory = sp.getString(SEARCH_HISTORY, "");
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
            convertView = mInflater.inflate(R.layout.lesson_playstudy_item, null);
            holder = new ViewHolder();
            holder.lesson_name = (TextView) convertView.findViewById(R.id.lesson_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.lesson_name.setText(mData.get(position).get("lesson_name").toString());
        return convertView;
    }

    public static class ViewHolder {
        public TextView lesson_name;
        public TextView hit;
    }
}