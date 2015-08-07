package com.xiaoteng.dms.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.entity.UpdataUserEntity;
import com.xiaoteng.dms.http.Http;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InputPasswordActivity extends Activity{
	private Button but_unlearn;//忘记密码
	private ImageView backing;//返回上一级
	private Button login_but;//确定
	private ImageButton ps_delete_bg;//清空内容
	private EditText passwd_et;//输入框
	private static TextView get_phone;//用于获取Home中的的用户号码
	
	private static InputPasswordActivity inputPasswordActivity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_inpasswd);
		intnet();
		
		 // 用textphone.getStringExtra()来得到上一个PromptActivity发过来的字符串
		Intent textphone =getIntent();
		String tvphone = textphone.getStringExtra("textphone");
		get_phone.setText(tvphone);
		
		
	}
	public void intnet(){
		inputPasswordActivity = this;
		
		get_phone = (TextView) findViewById(R.id.get_phone);
		backing = (ImageView) findViewById(R.id.backing);
		passwd_et = (EditText) findViewById(R.id.passwd_et);
		ps_delete_bg = (ImageButton) findViewById(R.id.ps_delete_bg);
		but_unlearn = (Button) findViewById(R.id.but_unlearn);
		login_but = (Button) findViewById(R.id.login_but);
		
		
		backing.setOnClickListener(onclick);
		passwd_et.setOnClickListener(onclick);
		ps_delete_bg.setOnClickListener(onclick);
		but_unlearn.setOnClickListener(onclick);
		login_but.setOnClickListener(onclick);
	}
	//监听按钮
	public OnClickListener onclick = new OnClickListener() {
		private String paramNewSMS;
		private ArrayList<NameValuePair> paramsAnewSMS;
		private String paramsData;
		private ArrayList<NameValuePair> paramsUserData;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			//忘记密码
			case R.id.but_unlearn:
				String urlAnew = "http://www.linhoo.com.cn/educate/code/sendcode.api";
				// 重新发送验证码
				paramsAnewSMS = new ArrayList<NameValuePair>();
				paramsAnewSMS.add(new BasicNameValuePair("mobile",get_phone.getText().toString()));
				paramsAnewSMS.add(new BasicNameValuePair("sendtype","1"));
				paramNewSMS = URLEncodedUtils.format(paramsAnewSMS,"UTF-8");
				Http.yanzheng(urlAnew, paramNewSMS, 2);
				
				break;
			//返回上一级
			case R.id.backing:
				Intent backing = new Intent();
				backing.setClass(inputPasswordActivity,HomeActivity.class);
				startActivity(backing);
				break;
				//确认进入下一步
			case R.id.login_but:
				//获取输入框密码
				String password = passwd_et.getText().toString();
				if(password.length()<=0){
					Toast.makeText(InputPasswordActivity.this, "密码不能为空", 0).show();
				}else if(password.length()<6){
					Toast.makeText(InputPasswordActivity.this,"密码长度不能少于6位", 0).show();
				}else if(password.length()>=12){
					Toast.makeText(InputPasswordActivity.this,"密码长度不能大于12位", 0).show();
				}else{
					// 获取存储的数据
					   SharedPreferences sp = getSharedPreferences("SETTING_INFOS", MODE_PRIVATE); 
					    String savepassword = sp.getString("PASSWORD", "");
					    System.err.println("密码为："+savepassword);
					  if (savepassword.equals(password)) {
						  //用户登陆调用接口
						  	String userData = "http://www.linhoo.com.cn/educate/user/login.api";
						  	paramsUserData = new ArrayList<NameValuePair>();
						  	paramsUserData.add(new BasicNameValuePair("mobile",get_phone.getText().toString()));
						  	paramsUserData.add(new BasicNameValuePair("pwd", passwd_et.getText().toString()));
						  	paramsUserData.add(new BasicNameValuePair("push_alias", "zach"));
						  	paramsData = URLEncodedUtils.format(paramsUserData,"UTF-8");
						  	Http.yanzheng(userData, paramsData, 6);
					        
					    }else{
					         Toast.makeText(InputPasswordActivity.this, "密码错误！", 0).show();
					    } 
				}
				
				break;
				//清空输入框
			case R.id.ps_delete_bg:
				EditText passwd_et = (EditText) findViewById(R.id.passwd_et);
				passwd_et.setText("");
				break;
			default:
				break;
			}
		}
	};
	public static Handler hd = new Handler() {
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
								Intent unlearn = new Intent();
								//传递用户号码给NewPasswordActivity
								unlearn.setClass(inputPasswordActivity,NewPasswordActivity.class);
								unlearn.putExtra("textphone", get_phone.getText().toString().trim());
								inputPasswordActivity.startActivity(unlearn);
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
								if(imgurl.equals(""))
								{
									imgurl = "default_s.png";
								}
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
								UpdataUserEntity.setUserid(userid);
								UpdataUserEntity.setNickname(nickname);
								UpdataUserEntity.setImgurl(imgurl);
								UpdataUserEntity.setPush_alias(push_alias);
								UpdataUserEntity.setPush_tag(push_tag);
								UpdataUserEntity.setIm_username(im_username);
								UpdataUserEntity.setIm_password(im_password);
								UpdataUserEntity.setVip_type(vip_type);
								UpdataUserEntity.setVip_begin(vip_begin);
								UpdataUserEntity.setVip_expire(vip_expire);
								UpdataUserEntity.setToken(token);
								 //实现界面的跳转
								Intent intent = new Intent(inputPasswordActivity,HomeActivity.class);
								intent.putExtra("userid", userid);
								intent.putExtra("userPhone", get_phone.getText().toString().trim());
								inputPasswordActivity.startActivity(intent);
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						break;
					default:
						break;
					}
		}
	};
		
}
