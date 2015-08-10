package com.xiaoteng.dms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.adapter.ListMsgAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MsgActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView list;
    private static final int REFRESH_COMPLETE = 0X110;
    private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private ImageView backing_bg;
    private ListMsgAdapter mAdapter;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java", "Javascript", "C++", "Ruby", "Json",
            "HTML"));

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    mAdapter.addData("Lucene", "Canvas", "Bitmap");
                    mAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;

            }
        };
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_msg);
        mListView = (ListView) findViewById(R.id.listViewMsg);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.widgetSwipeRefresh);
        backing_bg = (ImageView) findViewById(R.id.backing_bg);
        
        mSwipeLayout.setOnClickListener(onClick);
        backing_bg.setOnClickListener(onClick);
        
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light, android.R.color.holo_green_light);
        mAdapter=new ListMsgAdapter(this);
        mListView.setAdapter(mAdapter);
    }
    public OnClickListener onClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.backing_bg:
				Intent backing = new Intent();
				backing.setClass(MsgActivity.this,HomeActivity.class);
				startActivity(backing);
				break;
			case R.id.widgetSwipeRefresh:
				Intent widgetSwipeRefresh = new Intent();
				widgetSwipeRefresh.setClass(MsgActivity.this,HomeActivity.class);
				startActivity(widgetSwipeRefresh);
				break;

			default:
				break;
			}
		}
	};

    public void onRefresh()
    {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }
}