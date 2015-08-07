package com.xiaoteng.dms.activity.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.adapter.LessonPlaystudyAdapter.ViewHolder;
import com.xiaoteng.dms.dialog.PayDialog;

/**
 * 课程学习
 * @author zach
 *
 */
public class FragmentDetailed3 extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public FragmentDetailed3(Context context) {
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        mData=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name_tv", i+"小明");
            map.put("date_tv", "2014-4-23");
            map.put("news_tv", "课程讲的很好,我受益匪浅");
            mData.add(map);
        }
    }

    public void addData(String name_tv, String data_tv,String news_tv) {
        Map<String, Object> mapadd = new HashMap<String, Object>();
        mapadd.put("name_tv", name_tv);
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
            convertView = mInflater.inflate(R.layout.fragment_item_detailed3, null);
            holder = new ViewHolder();
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.date_tv = (TextView) convertView.findViewById(R.id.date_tv);
            holder.news_tv = (TextView) convertView.findViewById(R.id.news_tv);
            convertView.setTag(holder);
        } else {
        	holder = (ViewHolder)convertView.getTag();
        }
        holder.name_tv.setText(mData.get(position).get("name_tv").toString());
        holder.date_tv.setText(mData.get(position).get("date_tv").toString());
        holder.news_tv.setText(mData.get(position).get("news_tv").toString());
        return convertView;
    }
    public static class ViewHolder {
        public TextView name_tv,date_tv,news_tv;
    }
}