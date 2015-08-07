package com.xiaoteng.dms.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.xiaoteng.dms.R;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewestLessonAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public NewestLessonAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        mData=new ArrayList<Map<String, Object>>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://www.linhoo.com.cn/educate/lesson/index.api";
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
    }
    
    public void addData(String lesson_name, String hit) {
        Map<String, Object> mapadd = new HashMap<String, Object>();
        mapadd.put("lesson_name", lesson_name);
        mapadd.put("hit", hit);
        mData.add(0,mapadd);
    }

    public void modifyData() {
        mData.clear();
        for (int i = 0; i < 30; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("lesson_name", "第" + (i + 1) + "xxzx");
            map.put("hit", "38人正在学 ");
            mData.add(map);
        }
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
            convertView = mInflater.inflate(R.layout.newest_lesson_item, null);
            holder = new ViewHolder();
            holder.lesson_name = (TextView) convertView.findViewById(R.id.lesson_name);
            holder.hit = (TextView) convertView.findViewById(R.id.hit);
            //holder.imgurl = (ImageView) convertView.findViewById(R.id.course_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.lesson_name.setText(mData.get(position).get("lesson_name").toString());
        holder.hit.setText(mData.get(position).get("hit").toString());
        //holder.imgurl.setImageURI(Uri.parse(mData.get(position).get("imgurl").toString()));
        return convertView;
    }

    public static class ViewHolder {
        public TextView lesson_name;
        public TextView hit;
        //public ImageView imgurl;
    }
}