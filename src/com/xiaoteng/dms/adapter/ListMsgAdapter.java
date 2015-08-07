package com.xiaoteng.dms.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoteng.dms.R;
/**
 * 我的消息
 * @author zach
 *
 */
public class ListMsgAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public ListMsgAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        mData=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", "第" + (i + 1) + "行的标题");
            map.put("datetime", "2015-06-15 14:58:00");
            mData.add(map);
        }
    }

    public void addData(String title, String download, String datetime) {
        Map<String, Object> mapadd = new HashMap<String, Object>();
        mapadd.put("title", title);
        mapadd.put("datetime", datetime);
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
            convertView = mInflater.inflate(R.layout.listcomponent_msg, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.TextTitleMsg);
            holder.datetime = (TextView) convertView.findViewById(R.id.TextDateTimeMsg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(mData.get(position).get("title").toString());
        holder.datetime.setText(mData.get(position).get("datetime").toString());
        return convertView;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView datetime;
    }
}