package com.xiaoteng.dms.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xiaoteng.dms.R;
import com.xiaoteng.dms.uitl.Codes;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonPlaystudyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public LessonPlaystudyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        String url = Codes.URL_ROOT + "/lesson/studying.api";
        //SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        //String longhistory = sp.getString(SEARCH_HISTORY, "");

        mData=new ArrayList<Map<String, Object>>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    List<String> objects = new ArrayList<String>();
                    JSONArray resultlist = new JSONArray();
                    int count=0;
                    try {
                        JSONObject data = response.getJSONObject("data");
                        count= data.getInt("totalcount");
                        resultlist = data.getJSONArray("list");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < count; i++) {
                        try {
                            JSONObject obj = resultlist.getJSONObject(i);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("lesson_name", obj.getString("lessonname"));
                            map.put("hit", obj.getString("hit"));
                            //map.put("imgurl", obj.getString("imgurl"));
                            mData.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });





        mData=new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("lesson_name", "第" + (i + 1) + "行的标题");
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