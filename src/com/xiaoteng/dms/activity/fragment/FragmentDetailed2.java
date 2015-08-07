package com.xiaoteng.dms.activity.fragment;

import android.content.Context;
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

/**
 * 课程学习
 * @author zach
 *
 */
public class FragmentDetailed2 extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public FragmentDetailed2(Context context) {
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        mData=new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("lesson_summary_content", "本课程十一C++作为编程语言,讲诉C++面向对象的基础知识, 以及卡噶控制台应用程序的知识。主要内容包括：C++面向对象的思想,类的组合,继承,多台,综合编程实践，通过系统的学习,帮助学习者提高IT技能的实践能力。");
            map.put("text_summary","本课程是以C++作为变成语言,讲诉C++面向对象的基础知识,以及卡噶控制台应用程序的知识.主要内容包括：C++面向对象思想");
            mData.add(map);
        }

    public void addData(String lesson_summary_content, String text_summary) {
        Map<String, Object> mapadd = new HashMap<String, Object>();
        mapadd.put("lesson_summary_content", lesson_summary_content);
        mapadd.put("text_summary", text_summary);
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
            convertView = mInflater.inflate(R.layout.fragment_item_detailed2, null);
            holder = new ViewHolder();
            holder.lesson_summary_content = (TextView) convertView.findViewById(R.id.lesson_summary_content);
            holder.text_summary = (TextView) convertView.findViewById(R.id.text_summary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.lesson_summary_content.setText(mData.get(position).get("lesson_summary_content").toString());
        holder.text_summary.setText(mData.get(position).get("text_summary").toString());
        return convertView;
    }

    public static class ViewHolder {
        public TextView lesson_summary_content;
        public TextView text_summary;
        private ImageView author_image;
    }
}