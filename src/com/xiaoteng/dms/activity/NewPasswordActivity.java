package com.xiaoteng.dms.activity;

import java.util.ArrayList;

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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewPasswordActivity extends Activity {
	// 确定
	private Button login_but;
	// 清空文本
	private ImageButton delete_bg, delete_bg2;
	// 重发验证码
	private Button but_return;
	// 返回上一级
	private ImageView backing_iv;
	// 验证信息要发送的号码
	private static TextView text_phone_title2;
	private static EditText input_passwd_et;
	// 验证码输入框
	private static EditText test_et;
	private static NewPasswordActivity newPasswordActivity;
	
	
	private ArrayList<NameValuePair> params;
	private String param;
	private ArrayList<NameValuePair> paramuser;
	private ArrayList<NameValuePair> paramsAnewSMS;
	private String paramNewSMS;
	private ArrayList<NameValuePair> paramsUserData;
	private String paramsData;
	public static final String SETTING_INFOS = "SETTING_Infos";
	public static final String PASSWORD = "PASSWORD";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_new_password);

		newPasswordActivity = this;

		login_but = (Button) findViewById(R.id.login_but);
		delete_bg2 = (ImageButton) findViewById(R.id.delete_bg2);
		but_return = (Button) findViewById(R.id.but_return);
		backing_iv = (ImageView) findViewById(R.id.backing_iv);
		//用来接收密码
		input_passwd_et = (EditText) findViewById(R.id.input_passwd_et);
		
		
		but_return.setOnClickListener(onclick);
		login_but.setOnClickListener(onclick);
		delete_bg2.setOnClickListener(onclick);
		backing_iv.setOnClickListener(onclick);
		input_passwd_et.setOnClickListener(onclick);

		
		// 获取用户 登录/已注册号码
		text_phone_title2 = (TextView) findViewById(R.id.text_phone_title2);
		// 获取验证码
		test_et = (EditText) findViewById(R.id.test_et);
		Intent intentPhone = getIntent();
		// 用intent1.getStringExtra()来得到HomeActivity发来的字符串
		String login_text_Phone = intentPhone.getStringExtra("textPhone");
		// 接受未注册账号
		String new_Text_Phone = intentPhone.getStringExtra("textPhone");
		// 获取验证码
		String testphone = intentPhone.getStringExtra("code");
		test_et.setText(testphone);
		text_phone_title2.setText(login_text_Phone);
		text_phone_title2.setText(new_Text_Phone);
		
		/*获取一个SharedPreferences对象 */
		SharedPreferences UserPass= getSharedPreferences(SETTING_INFOS, Activity.MODE_PRIVATE);
		//取出保存的NAME
		String pass = UserPass.getString(PASSWORD, "");
		input_passwd_et.setText(pass); //将取出来的密码赋予input_passwd_et
		
		// 判断清楚按钮是否隐藏并显示
		input_passwd_et.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// 监听判断test_et值是否为空(0),为0显示delete_bg
				if (input_passwd_et.length() != 0) {
					if (delete_bg2.getVisibility() == View.GONE) {
						delete_bg2.setVisibility(View.VISIBLE);

					}
					if (input_passwd_et.length() < 0) {
						delete_bg2.setVisibility(View.GONE);
					}
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});

	}
	
	public void SavePass(){
		//获取一个SharedPreferences对象 
		SharedPreferences settings = getSharedPreferences("SETTING_INFOS", 0);
		//保存密码
		settings.edit().putString(PASSWORD, input_passwd_et.getText().toString()).commit();
		// 获取存储的数据
		   SharedPreferences sp = getSharedPreferences("SETTING_INFOS", MODE_PRIVATE); 
		    String savepassword = sp.getString(PASSWORD, "");
		    if(savepassword.equals("")){
				Toast.makeText(newPasswordActivity, "密码不能为空", 0).show();
			}else if(savepassword.length()<6){
				Toast.makeText(newPasswordActivity,"密码长度不能少于6位", 0).show();
			}else if(savepassword.length()>=12){
				Toast.makeText(newPasswordActivity,"密码长度不能大于12位", 0).show();
			}else{
			Toast.makeText(newPasswordActivity, "密码注册成功", Toast.LENGTH_LONG).show();
			//用户登陆调用接口
		  	String userData = "http://www.linhoo.com.cn/educate/user/login.api";
		  	paramsUserData = new ArrayList<NameValuePair>();
		  	paramsUserData.add(new BasicNameValuePair("mobile",text_phone_title2.getText().toString()));
		  	paramsUserData.add(new BasicNameValuePair("pwd", input_passwd_et.getText().toString()));
		  	paramsUserData.add(new BasicNameValuePair("push_alias", "zach"));
		  	paramsData = URLEncodedUtils.format(paramsUserData,"UTF-8");
		  	Http.yanzheng(userData, paramsData, 6);
			finish();
		}
	}

	public OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			// 确定
			case R.id.login_but:
				String url = "http://www.linhoo.com.cn/educate/code/validcode.api";
				// 带参（手机号mobile、验证码code）
				params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("mobile", text_phone_title2
						.getText().toString()));
				params.add(new BasicNameValuePair("code", test_et.getText()
						.toString()));
				param = URLEncodedUtils.format(params, "UTF-8");
				Http.yanzheng(url, param, 3);
				SavePass();
				break;
			// 清空文本
			case R.id.delete_bg2:
				TextView input_passwd_et = (TextView) findViewById(R.id.input_passwd_et);
				input_passwd_et.setText("");
				break;
			// 重发验证码
			case R.id.but_return:
				sensmg();
				break;
			// 返回上一级
			case R.id.backing_iv:
				Intent backing = new Intent();
				backing.setClass(NewPasswordActivity.this,
						InputPasswordActivity.class);
				startActivity(backing);
				break;
			default:
				break;
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onkeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent myIntent = new Intent();
			myIntent = new Intent(NewPasswordActivity.this,
					InputPasswordActivity.class);
			startActivity(myIntent);
			this.fileList();
		}
		return super.onKeyDown(keyCode, event);
	}
	public void sensmg(){
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.err.println("false");
				String urlAnew = "http://www.linhoo.com.cn/educate/code/sendcode.api";
				// 重新发送验证码
				paramsAnewSMS = new ArrayList<NameValuePair>();
				paramsAnewSMS.add(new BasicNameValuePair("mobile",text_phone_title2.getText().toString()));
				paramsAnewSMS.add(new BasicNameValuePair("sendtype","1"));
				paramNewSMS = URLEncodedUtils.format(paramsAnewSMS,"UTF-8");
				Http.yanzheng(urlAnew, paramNewSMS, 5);
			}
		});
		th.start();
	}
	public static Handler hd = new Handler() {
		private String paramUserRegister;
		private ArrayList<NameValuePair> paramRegister;

		public void handleMessage(Message msg) {
			// 请求数据返回的消息
			String httpResult = (String) msg.obj;
			// 判断what值
			switch (msg.what) {
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
						System.err.println("验证码："+code);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					//解析验证码是否为true
					JSONObject json = new JSONObject(httpResult);
					System.err.println(json);
					 int retcode = json.getInt("retcode");
					 if(retcode==0){
					 JSONObject testRegister = json.getJSONObject("data");
					 boolean isRight = testRegister.getBoolean("isright");
					 if(isRight){
						 System.err.println("true");
							// 未注册
							// 发送验证码
							String urlRegister = "http://www.linhoo.com.cn/educate/user/register.api";
							paramRegister = new ArrayList<NameValuePair>();
							paramRegister.add(new BasicNameValuePair("mobile", text_phone_title2.getText().toString()));
							paramRegister.add(new BasicNameValuePair("pwd", input_passwd_et.getText().toString()));
							paramRegister.add(new BasicNameValuePair("code", test_et.getText().toString()));
							paramRegister.add(new BasicNameValuePair("push_alias", "zach"));
							paramUserRegister = URLEncodedUtils.format(paramRegister, "UTF-8");
							Http.yanzheng(urlRegister, paramUserRegister, 4);
					 }
					 else{
						 System.err.println("false:验证码错误");
						 Toast.makeText(newPasswordActivity, "密码错误！", Toast.LENGTH_LONG).show();
					 }
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case 4:
				//解析用户注册
				try {
					JSONObject jsonRegister = new JSONObject(httpResult);
					int retcode = jsonRegister.getInt("retcode");
					System.err.println(jsonRegister);
					if(retcode == 0){
						JSONObject data = jsonRegister.getJSONObject("data");
						int userid = data.getInt("userid");
						String im_username = data.getString("im_username");
						String im_password = data.getString("im_password");
						String vip_type = data.getString("vip_type");
						long vip_begin = data.getLong("vip_begin");
						long vip_expire = data.getLong("vip_expire");
						String token = data.getString("token");
					}else{
						
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 5:
				// 验证码重发
				try {
					JSONObject json = new JSONObject(httpResult);
					System.err.println(json);
					int retcode = json.getInt("retcode");
					if (retcode == 0) {
						JSONObject data = json.getJSONObject("data");
						int resendTime = data.getInt("resendtime");
						String code = data.getString("code");
						test_et.setText(code);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 6:
					//登陆
					try {
						JSONObject json = new JSONObject(httpResult);
						System.err.println(json);
						int retcode = json.getInt("retcode");
						if (retcode == 0) {
							JSONObject data = json.getJSONObject("data");
							int userid = data.getInt("userid");
							String nickname = data.getString("nickname");
							String imgurl = data.getString("imgurl");
							String push_alias = data.getString("push_alias");
							String push_tag = data.getString("push_tag");
							String im_username = data.getString("im_username");
							String im_password = data.getString("im_password");
							String vip_type = data.getString("vip_type");
							String vip_expire = "";
							String vip_begin = "";
							if(vip_type.equals("TIME_VIP")){
								 vip_expire = data.getString("vip_expire");
							}else if (vip_type.equals("NOTIME_VIP")) {
								vip_begin = data.getString("vip_begin");
								vip_expire = data.getString("vip_expire");
							}
							String token = data.getString("token");
							 //实现界面的跳转
							Intent intent = new Intent(newPasswordActivity,HomeActivity.class);
							intent.putExtra("userPhone", text_phone_title2.getText().toString().trim());
							intent.putExtra("nickname", nickname);//用户名
							intent.putExtra("imgurl", imgurl);//用户头像
							intent.putExtra("push_alias", push_alias);//用户昵称
							intent.putExtra("vip_type", vip_type);//非会员
							intent.putExtra("vip_begin", vip_begin);//是会员
							intent.putExtra("vip_expire", vip_expire);//限时会员
							newPasswordActivity.startActivity(intent);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					break;
				}
			};
	};
}
