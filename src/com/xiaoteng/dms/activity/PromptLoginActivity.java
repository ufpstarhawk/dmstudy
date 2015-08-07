package com.xiaoteng.dms.activity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.http.Http;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PromptLoginActivity extends Activity {
	// 用于显示输入对应号码
	private TextView text_phone;
	private Button next_but;
	private ImageButton delete_bg;
	private static EditText input_phone_et;
	private long exitTime = 0;// 定义一个变量，来标识是否退出
	private ArrayList<NameValuePair> params;
	private String param;
	private static PromptLoginActivity promptLonginActivity;
	// 搜索、消息、设置
	private ImageView search, msg, settings;
	
	
	public static final String SETTING_INFOS = "SETTING_Infos";
	public static final String NAME = "NAME"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_prompt);

		promptLonginActivity = this;

		search = (ImageView) findViewById(R.id.search);
		msg = (ImageView) findViewById(R.id.msg);
		settings = (ImageView) findViewById(R.id.settings);
		search.setOnClickListener(onclick);
		msg.setOnClickListener(onclick);
		settings.setOnClickListener(onclick);

		next_but = (Button) findViewById(R.id.next_but);
		delete_bg = (ImageButton) findViewById(R.id.delete_bg);
		/*获取用来输入用户名的组件 */
		input_phone_et = (EditText) findViewById(R.id.input_phone_et);
		text_phone = (TextView) findViewById(R.id.text_phone);

		next_but.setOnClickListener(onclick);
		delete_bg.setOnClickListener(onclick);
		
		/*获取一个SharedPreferences对象 */
		SharedPreferences Username = getSharedPreferences(SETTING_INFOS, Activity.MODE_PRIVATE);
		//取出保存的NAME
		String name = Username.getString(NAME, "");
		input_phone_et.setText(name); //将取出来的用户名赋予input_phone_et
		
		/***
		 * 方法（一） 显示输入对应号码 只能监听硬键盘
		 */
		// input_phone_et.setOnKeyListener(new View.OnKeyListener() {
		// @Override
		// public boolean onKey(View v, int keyCode, KeyEvent event) {
		// text_phone.setText(input_phone_et.getText().toString());
		// return false;
		// }
		// });
		/***
		 * 方法（二） 显示输入对应号码 监听硬键盘和软键盘
		 */
		input_phone_et.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				text_phone.setText(input_phone_et.getText());
				// 监听判断TextView字符串值是否为空(0),为0显示TextView
				if (input_phone_et.length() != 0) {
					if (text_phone.getVisibility() == View.GONE
							&& delete_bg.getVisibility() == View.GONE) {
						text_phone.setVisibility(View.VISIBLE);
						delete_bg.setVisibility(View.VISIBLE);

					}
					if (input_phone_et.length() < 0) {
						text_phone.setVisibility(View.GONE);
						delete_bg.setVisibility(View.GONE);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}
	protected void onStop(){
		super.onStop();
		//获取一个SharedPreferences对象 
		SharedPreferences settings = getSharedPreferences("SETTING_INFOS", 0);
		//保存用户名
		settings.edit().putString(NAME, input_phone_et.getText().toString()).commit();
		if(settings.equals("")){
			Toast.makeText(promptLonginActivity, "账号为空！", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	public OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.search:
				Intent search = new Intent();
				search.setClass(PromptLoginActivity.this, SearchActivity.class);
				startActivity(search);
				break;
			case R.id.msg:
				Intent msg = new Intent();
				msg.setClass(PromptLoginActivity.this, MsgActivity.class);
				startActivity(msg);
				break;
			case R.id.settings:
				Intent settings = new Intent();
				settings.setClass(PromptLoginActivity.this,
						SettingsActivity.class);
				startActivity(settings);
				break;

			case R.id.next_but:
				Boolean isPhone = isPhone(input_phone_et.getText().toString());
				if(isPhone){
					String url = "http://www.linhoo.com.cn/educate/user/mobileexist.api";
					// 判断账号是否已注册
					// 带参
					params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("mobile", input_phone_et
							.getText().toString()));
					param = URLEncodedUtils.format(params, "UTF-8");
					Http.yanzheng(url, param, 1);
				}else{
					Toast.makeText(promptLonginActivity, "手机号码格式不正确请重新输入！",Toast.LENGTH_LONG).show();
				}
				break;
			// 清空输入框
			case R.id.delete_bg:
				EditText input_phone_et = (EditText) findViewById(R.id.input_phone_et);
				input_phone_et.setText("");
				break;
			default:
				break;
			}
		}
	};
	//手机号码格式
	public boolean isPhone(String strPhone) {
		String strPattern = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strPhone);
		return m.matches();
	}
	
	public static Handler h = new Handler() {

		private ArrayList<NameValuePair> paramsSMS;
		private String paramSMS;

		public void handleMessage(Message msg) {
			// 请求数据返回的消息
			String httpResult = (String) msg.obj;

			// 判断what值
			switch (msg.what) {
			// 查询是否登陆
			case 1:
				try {
					// json解析
					JSONObject json = new JSONObject(httpResult);
					System.err.println(json);
					int retcode = json.getInt("retcode");
					if (retcode == 0) {
						JSONObject data = json.getJSONObject("data");
						boolean isExist = data.getBoolean("isexist");
						if (isExist) {
							System.err.println("true");
							Intent intent = new Intent();
							intent.setClass(promptLonginActivity,
									InputPasswordActivity.class);
							// 传递name参数为text_phone.getText()
							intent.putExtra("textphone", input_phone_et.getText().toString().trim());
							promptLonginActivity.startActivity(intent);
						} else {
							System.err.println("false");
							String url = "http://www.linhoo.com.cn/educate/code/sendcode.api";
							// 未注册
							// 发送验证码
							paramsSMS = new ArrayList<NameValuePair>();
							paramsSMS.add(new BasicNameValuePair("mobile",
									input_phone_et.getText().toString()));
							paramsSMS.add(new BasicNameValuePair("sendtype",
									"1"));
							paramSMS = URLEncodedUtils.format(paramsSMS,
									"UTF-8");
							Http.yanzheng(url, paramSMS, 2);

						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				// json解析
				try {
					JSONObject json = new JSONObject(httpResult);
					System.err.println(json);
					int retcode = json.getInt("retcode");
					if (retcode == 0) {
						JSONObject data = json.getJSONObject("data");
						int resendTime = data.getInt("resendtime");
						String code = data.getString("code");
						Intent intent = new Intent();
						intent.putExtra("textPhone", input_phone_et.getText().toString().trim());
						intent.setClass(promptLonginActivity,NewPasswordActivity.class);
						// 传递name参数为text_phone.getText()
						intent.putExtra("code", code);
						promptLonginActivity.startActivity(intent);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		};
	};
}
