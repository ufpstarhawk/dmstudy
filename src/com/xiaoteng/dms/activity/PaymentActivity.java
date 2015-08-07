package com.xiaoteng.dms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.xiaoteng.dms.R;
/**
 * 购买章节
 * @author zach
 *
 */
public class PaymentActivity extends Activity {
    private Spinner spinnerBuyChapter;
    private ArrayAdapter adapter;
    private static final String[] array={"全部章节   ￥299.00","全部章节   ￥299.00","全部章节   ￥299.00"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_payment);

        spinnerBuyChapter = (Spinner) findViewById(R.id.spinnerBuyChapter);
        //adapter = ArrayAdapter.createFromResource(this, R.array.plantes, android.R.layout.simple_spinner_item);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBuyChapter.setAdapter(adapter);
        spinnerBuyChapter.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
        spinnerBuyChapter.setVisibility(View.VISIBLE);
    }

    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
           // view2.setText("你使用什么样的手机："+adapter2.getItem(arg2));
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
}
