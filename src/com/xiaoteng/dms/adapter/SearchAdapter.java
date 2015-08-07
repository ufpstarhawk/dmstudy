package com.xiaoteng.dms.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaoteng.dms.activity.SearchActivity;
import com.xiaoteng.dms.data.SearchHistoryData;
import com.xiaoteng.dms.R;

public class SearchAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<com.xiaoteng.dms.data.SearchHistoryData> mOriginalValues;// 所有的Item
    private List<SearchHistoryData> mObjects;// 过滤后的item
    private final Object mLock = new Object();
    private int mMaxMatch = 10;// 最多显示多少个选项,负数表示全部
    private OnClickListener mOnClickListener;

    public SearchAdapter(Context context, int maxMatch,
                             OnClickListener onClickListener) {
        this.mContext = context;
        this.mMaxMatch = maxMatch;
        this.mOnClickListener = onClickListener;
        initSearchHistory();
        mObjects = mOriginalValues;
    }

    @Override
    public int getCount() {
        return null == mObjects ? 0 : mObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return null == mObjects ? 0 : mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AutoHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.listcomponent_search_item, parent, false);
            holder = new AutoHolder();
            holder.content = (TextView) convertView
                    .findViewById(R.id.textHistory);
            holder.addButton = (TextView) convertView
                    .findViewById(R.id.textAdd);
            convertView.setTag(holder);
        } else {
            holder = (AutoHolder) convertView.getTag();
        }

        SearchHistoryData data = mObjects.get(position);
        holder.content.setText(data.getContent());
        holder.addButton.setTag(data);
        holder.addButton.setOnClickListener(mOnClickListener);
        return convertView;
    }

    public void initSearchHistory() {
        SharedPreferences sp = mContext.getSharedPreferences(
                SearchActivity.SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SearchActivity.SEARCH_HISTORY, "");
        String[] hisArrays = longhistory.split(",");
        mOriginalValues = new ArrayList<SearchHistoryData>();
        if (hisArrays.length < 1) {
            return;
        }
        for (int i = 0; i < hisArrays.length; i++) {
            mOriginalValues.add(new SearchHistoryData().setContent(hisArrays[i]));
        }
    }

    public void performFiltering(CharSequence prefix) {
        if (prefix == null || prefix.length() == 0) {//搜索框内容为空的时候显示所有历史记录
            synchronized (mLock) {
                mObjects = mOriginalValues;
            }
        } else {
            String prefixString = prefix.toString().toLowerCase();
            int count = mOriginalValues.size();
            ArrayList<SearchHistoryData> newValues = new ArrayList<SearchHistoryData>(count);
            for (int i = 0; i < count; i++) {
                final String value = mOriginalValues.get(i).getContent();
                final String valueText = value.toLowerCase();
                if (valueText.contains(prefixString)) {

                }
                if (valueText.startsWith(prefixString)) {
                    newValues.add(new SearchHistoryData().setContent(valueText));
                } else {
                    final String[] words = valueText.split(" ");
                    final int wordCount = words.length;
                    for (int k = 0; k < wordCount; k++) {
                        if (words[k].startsWith(prefixString)) {
                            newValues.add(new SearchHistoryData()
                                    .setContent(value));
                            break;
                        }
                    }
                }
                if (mMaxMatch > 0) {
                    if (newValues.size() > mMaxMatch - 1) {
                        break;
                    }
                }
            }
            mObjects = newValues;
        }
        notifyDataSetChanged();
    }

    public void clearList() {
        SharedPreferences sp = mContext.getSharedPreferences(
                SearchActivity.SEARCH_HISTORY, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        mObjects=null;
        notifyDataSetChanged();
    }

    private class AutoHolder {
        TextView content;
        TextView addButton;
    }
}