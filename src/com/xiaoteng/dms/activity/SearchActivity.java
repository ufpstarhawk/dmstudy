package com.xiaoteng.dms.activity;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.activity.fragment.SearchResultFragment;
import com.xiaoteng.dms.adapter.SearchAdapter;
import com.xiaoteng.dms.data.SearchHistoryData;

public class SearchActivity<SearchHistSoryData> extends Activity implements OnClickListener {
    public static final String SEARCH_HISTORY = "search_history";
    private ListView listHistory;
    private ImageButton btnSearch;
    private TextView textSearch;
    private SearchAdapter searchAdapter;
    private Button btnClearHistory;
    private SearchResultFragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {
        searchAdapter = new SearchAdapter(this, -1, this);
        listHistory = (ListView) findViewById(R.id.listviewHistory);
        listHistory.setAdapter(searchAdapter);
        listHistory.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
                SearchHistoryData data = (SearchHistoryData) searchAdapter.getItem(position);
                textSearch.setText(data.getContent());
                btnSearch.performClick();
            }
        });

        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        textSearch = (TextView) findViewById(R.id.textSearch);
        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                searchAdapter.performFiltering(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnClearHistory = (Button) findViewById(R.id.btnClearHistory);
        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdapter.clearList();
                searchAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnSearch) {//搜索按钮
            saveSearchHistory();
            searchAdapter.initSearchHistory();
            //搜索操作
            /*FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            searchResultFragment = new SearchResultFragment();
            transaction.replace(R.id.fragment_content,searchResultFragment);
            transaction.commit();*/
        } else {//"+"号按钮
            SearchHistoryData data = (SearchHistoryData) v.getTag();
            textSearch.setText(data.getContent());
        }
    }

    private void saveSearchHistory() {
        String text = textSearch.getText().toString().trim();
        if (text.length() < 1) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");
        ArrayList<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (text.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, text);
        }
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
        }
    }
}