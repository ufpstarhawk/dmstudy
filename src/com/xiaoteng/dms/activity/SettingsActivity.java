package com.xiaoteng.dms.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import junit.framework.Test;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.R.string;
import com.xiaoteng.dms.dialog.OpenDialog;
import com.xiaoteng.dms.dialog.PayDialog;
import com.xiaoteng.dms.dialog.PayDialog.PayDialogBuilder;
import com.xiaoteng.dms.entity.PayVipEntity;
import com.xiaoteng.dms.entity.UpdataUserEntity;
import com.xiaoteng.dms.http.Http;

/**
 * Top设置
 * 
 * @author zach
 * 
 */
public class SettingsActivity extends Activity {

	private TextView tv_usernumber, tv_username;

	// 购买
	private Button btnPay1, btnPay2, btnPay3, btnLogout;
	private Button btnOpen;// 开通
	private ImageView backing_bg;
	private Button button;// 退出登陆
	private TextView tv_price1, tv_price2;// 优惠价格
	private ImageButton btnHead;
	private LinearLayout feedback, update;
	private static SettingsActivity settingsActivity;
	private static PayDialogBuilder builder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
		findView();
		init();
		settingsActivity = this;
		// 用户手机号码
		Intent userPhon = getIntent();
		String userPhone = userPhon.getStringExtra("userPhone");
		tv_usernumber.setText(userPhone);
		
		// 推送别名
		tv_username.setText(UpdataUserEntity.getPush_alias());
		
	}

	private void findView() {
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_usernumber = (TextView) findViewById(R.id.tv_usernumber);

		btnPay1 = (Button) findViewById(R.id.btnPay1);
		btnPay2 = (Button) findViewById(R.id.btnPay2);
		btnPay3 = (Button) findViewById(R.id.btnPay3);
		btnOpen = (Button) findViewById(R.id.btnOpen);

		backing_bg = (ImageView) findViewById(R.id.backing_bg);
		backing_bg.setOnClickListener(onClickBack);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		btnLogout.setOnClickListener(onClickLogout);
		feedback = (LinearLayout) findViewById(R.id.btnFeedback);
		feedback.setOnClickListener(onClickFeedBack);
		update = (LinearLayout) findViewById(R.id.btnUpdate);
		update.setOnClickListener(onClickUpdate);
		btnHead = (ImageButton) findViewById(R.id.btnHead);
		btnHead.setOnClickListener(onClickEditInfo);

		tv_price1 = (TextView) findViewById(R.id.tv_price1);
		tv_price2 = (TextView) findViewById(R.id.tv_price2);

		// 设置中划线
		tv_price1.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		tv_price2.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		// 设置字体颜色
		tv_price1
				.setText(Html
						.fromHtml("<font color=gray>￥108.00</font><font color=red>立享优惠￥90</font>"));
		tv_price2
				.setText(Html
						.fromHtml("<font color=gray>￥216.00</font><font color=red>立享优惠￥160</font>"));
		String str1 = "￥108.00立享优惠￥90";
		String str2 = "￥216.00立享优惠￥160";
		SpannableString ss = new SpannableString(str1);
		SpannableString ss2 = new SpannableString(str2);
		ss.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 7,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(Color.RED), 7, 14,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss2.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 7,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss2.setSpan(new ForegroundColorSpan(Color.RED), 7, 15,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_price1.setText(ss);
		tv_price2.setText(ss2);

	}

	public OnClickListener onClickLogout = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent newintent = new Intent();
			newintent
					.setClass(SettingsActivity.this, PromptLoginActivity.class);
			startActivity(newintent);
			SettingsActivity.this.finish();
		}
	};

	public OnClickListener onClickBack = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent newintent = new Intent();
			newintent.setClass(SettingsActivity.this, PaymentActivity.class);
			startActivity(newintent);
			SettingsActivity.this.finish();
		}
	};

	public OnClickListener onClickFeedBack = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent newintent = new Intent();
			newintent.setClass(SettingsActivity.this, FeedBackActivity.class);
			startActivity(newintent);
		}
	};

	public OnClickListener onClickUpdate = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// update
		}
	};
	// 修改用户信息
	public OnClickListener onClickEditInfo = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent();
			intent.setClass(settingsActivity, EditInfoActivity.class);
			settingsActivity.startActivity(intent);
		}
	};
	private ArrayList<NameValuePair> paramsPlayVip;
	private String params;

	

	public byte[] getImage(String path) throws IOException {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET"); // 设置请求方法为GET
		conn.setReadTimeout(5 * 1000); // 设置请求过时时间为5秒
		InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据
		byte[] data = readInputStream(inputStream); // 获得图片的二进制数据
		return data;
	}

	public static byte[] readInputStream(InputStream inputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	

	private void init() {
		// 获取网络图片
		System.err.println("imageurl>>>>>>>"+UpdataUserEntity.getImgurl());
		try {
			byte[] data = getImage(UpdataUserEntity.getImgurl());
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length); // 生成位图
			btnHead.setImageBitmap(bitmap); // 显示图片
			
		} catch (IOException e) {
		}

		 builder = new PayDialog.PayDialogBuilder(
				this);
		btnPay1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.create().show();
				PayVipEntity.setMonth(1);
			}
		});
		btnPay2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.create().show();
				PayVipEntity.setMonth(3);
			}
		});
		btnPay3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder.create().show();
				PayVipEntity.setMonth(12);
			}
		});
		final OpenDialog.OpenDialogBuilder builder1 = new OpenDialog.OpenDialogBuilder(
				this);
		btnOpen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				builder1.create(1).show();
			}
		});

	};
	   public static Handler hd = new Handler(){
			public void handleMessage(Message msg) {
				String httpResult = (String) msg.obj;
				switch (msg.what) {
				case 8:
					try {
						JSONObject jsonpay = new JSONObject(httpResult);
						System.err.println(jsonpay);
						int retcode = jsonpay.getInt("retcode");
						if (retcode == 0) {
							builder.create().cancel();
							JSONObject data = jsonpay.getJSONObject("data");
							String orderno = data.getString("orderno");
							System.err.println("orderno：>>>>>>>>>"+orderno);
							String transtr = data.getString("transtr");
							System.err.println("transtr：>>>>>>>>>"+transtr);
							int price = data.getInt("price");
							System.err.println("price：>>>>>>>>>"+price);
							String notifyurl = data.getString("notifyurl");
							System.err.println("notifyurl：>>>>>>>>>"+notifyurl);
							if(orderno.equals("")&&transtr.equals("")&&price == 0){
								Toast.makeText(settingsActivity, "支付失败！", Toast.LENGTH_SHORT).show();
							}else {
								PayDemoActivity payDemoActivity = new PayDemoActivity();
								payDemoActivity.pay();
							}
							
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				default:
					break;
				}
			}
		};
}
