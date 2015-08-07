package com.xiaoteng.dms.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.xiaoteng.dms.activity.EditInfoActivity;
import com.xiaoteng.dms.activity.InputPasswordActivity;
import com.xiaoteng.dms.activity.NewPasswordActivity;
import com.xiaoteng.dms.activity.PromptLoginActivity;
import com.xiaoteng.dms.activity.SettingsActivity;
import com.xiaoteng.dms.dialog.PayDialog;

import android.os.Message;

public class Http {
	public static void yanzheng(final String url ,final String param , final int zt){
    	Thread th = new Thread(new Runnable() {
			private HttpGet request;
			private DefaultHttpClient client;
			private BasicCookieStore localCookies;
			private String news;

			@Override
			public void run() {
		        //使用apache HTTP客户端实现    
		    	request = new HttpGet(url+"?"+param);
		        try {  
		        	//设置请求参数项    
		        	client =  new DefaultHttpClient();
		        	localCookies = new BasicCookieStore(); 
		        	client.setCookieStore(localCookies);
		        	System.err.println("url:"+url+"?"+param);
		            //执行请求  
		            HttpResponse response = client.execute(request); 
		            //判断是否请求成功  
		            if(response.getStatusLine().getStatusCode()==200) {  
		                //获得响应信息  
		            	news =   EntityUtils.toString(response.getEntity(), "utf-8");
		            	Message m = new Message();
		            	m.obj = news;
		            	//1验证是否注册
		            	//zt状态
		            	if(zt == 1){
							m.what = 1;
							PromptLoginActivity.h.sendMessage(m);
		            	}
		            	//2表示发验证码
		            	if (zt==2) {
		            		m.what = 2;
							PromptLoginActivity.h.sendMessage(m);
						}
		            	//3表示验证短信是否正确
		            	if (zt==3) {
		            		m.what = 3;
		            		NewPasswordActivity.hd.sendMessage(m);
						}
		            	//4表示用户注册
		            	if (zt==4) {
		            		m.what = 4;
		            		NewPasswordActivity.hd.sendMessage(m);
						}
		            	//5验证码重发
		            	if (zt==5) {
		            		m.what = 5;
		            		NewPasswordActivity.hd.sendMessage(m);
						}
		            	//6登陆用户信息
		            	if (zt==6) {
		            		m.what = 6;
		            		InputPasswordActivity.hd.sendMessage(m);
		            	} 
		            	//7修改用户信息
		            	if (zt==7) {
		            		m.what = 7;
		            		EditInfoActivity.hd.sendMessage(m);
		            	}
		            	//8修改用户信息
		            	if (zt==8) {
		            		m.what = 8;
		            		SettingsActivity.hd.sendMessage(m);
		            	}
		            	else{ 
		            	news = "cw";
		            }
	            }
		        }catch(Exception e)  {   
		            e.printStackTrace();  
		        }
			}
		});
		th.start();
    }
}
