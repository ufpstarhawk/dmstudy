package com.xiaoteng.dms.activity;

import java.io.File;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import com.xiaoteng.dms.R;
import com.xiaoteng.dms.entity.UpdataUserEntity;
import com.xiaoteng.dms.http.Http;
import com.xiaoteng.dms.uitl.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//个人信息设置
public class EditInfoActivity extends Activity{
	//组件
	private ImageView btnHeadImg;
    private ImageView backing_bg;
    private Button btnSubmit;
    private EditText textUsername;
    
    private static EditInfoActivity editInfoActivity;
    
    private String[] items = new String[] { "选择本地图片", "拍照" };

    /*头像名称*/
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    
    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_editinfo);
        btnHeadImg = (ImageView) findViewById(R.id.btnHeadImg);
        
        editInfoActivity = this;
        
        //设置事件监听
        btnHeadImg.setOnClickListener(listener);
        
        textUsername = (EditText) findViewById(R.id.textUsername);
        backing_bg = (ImageView) findViewById(R.id.backing_bg);
        backing_bg.setOnClickListener(onClickBack);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(onClickSubmit);
    }
    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
                showDialog();
        }
    };
    /**
     * 显示选择对话框
    */
        private void showDialog() {
                
                new AlertDialog.Builder(this).setItems(items, new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int which) {
                          switch (which) {
                           case 0:
                        	   Intent intentFromGallery = new Intent();
                        	   intentFromGallery.setType("image/*"); // 设置文件类型
                        	   intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                        	   startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
                            break;
                           case 1:
                        	   Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       // 判断存储卡是否可以用，可用进行存储
                      if (Tools.hasSdcard()) {
                    	  intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/"+IMAGE_FILE_NAME)));
                       }startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
                       break;
                          }
                    }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            	@Override
                public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                      }
            	}).show();
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                        startPhotoZoom(data.getData());
                        break;
                case CAMERA_REQUEST_CODE:
                	if (Tools.hasSdcard()) {
                        startPhotoZoom(Uri.fromFile(new File(Environment
                                           .getExternalStorageDirectory(),
                                           IMAGE_FILE_NAME)));
                } else {
                        Toast.makeText(EditInfoActivity.this, "未找到存储卡，无法存储照片！",
                                        Toast.LENGTH_LONG).show();
                }

                break;
                case RESULT_REQUEST_CODE:
                        if (data != null) {
                                getImageToView(data);
                        }
                        break;
                }
                super.onActivityResult(requestCode, resultCode, data);
        }
        /**
         * 裁剪图片方法实现
        * 
         * @param uri
         */
        public void startPhotoZoom(Uri uri) {

                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");
                // 设置裁剪
               intent.putExtra("crop", "true");
                // aspectX aspectY 是宽高的比例
               intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                // outputX outputY 是裁剪图片宽高
               intent.putExtra("outputX", 320);
                intent.putExtra("outputY", 320);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 2);
        }
        /**
         * 保存裁剪之后的图片数据
        * 
         * @param picdata
         */
        private void getImageToView(Intent data) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        Drawable drawable = new BitmapDrawable(photo);
                        btnHeadImg.setImageDrawable(drawable);
                }
        }

        

    public OnClickListener onClickBack = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent newintent = new Intent();
            newintent.setClass(EditInfoActivity.this, SettingsActivity.class);
            startActivity(newintent);
            EditInfoActivity.this.finish();
        }
    };

    public OnClickListener onClickSubmit = new OnClickListener() {
        @Override
        public void onClick(View v) {
        	Thread th = new Thread(new Runnable() {
				private ArrayList<NameValuePair> paramsUpdataUser;
				private String paramUpdata;

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String urlAnew = "http://www.linhoo.com.cn/educate/user/modifyinfo.api";
					paramsUpdataUser = new ArrayList<NameValuePair>();
					paramsUpdataUser.add(new BasicNameValuePair("userid", UpdataUserEntity.getUserid()+""));
					paramsUpdataUser.add(new BasicNameValuePair("nickname",UpdataUserEntity.getNickname()));
					paramsUpdataUser.add(new BasicNameValuePair("token", UpdataUserEntity.getToken()));
					paramsUpdataUser.add(new BasicNameValuePair("headimg", UpdataUserEntity.getImgurl()));
					paramUpdata = URLEncodedUtils.format(paramsUpdataUser,"UTF-8");
					Http.yanzheng(urlAnew, paramUpdata, 7);
				}
			});
			th.start();
        }
    };
    public static Handler hd = new Handler() {
		public void handleMessage(Message msg) {
			// 请求数据返回的消息
			String httpResult = (String) msg.obj;
			// 判断what值
			switch (msg.what) {
			// 修改用户信息
			case 7:
				try {
					JSONObject json = new JSONObject(httpResult);
					System.err.println(json);
					int retcode = json.getInt("retcode");
					if (retcode == 0) {
						JSONObject data = json.getJSONObject("data");
						int userid = data.getInt("userid");
						String nickname = data.getString("nickname");
						String token = data.getString("token");
						String headimg = data.getString("headimg");
						String byteimg = byteArr2HexStr(headimg.getBytes());
						Intent newintent = new Intent();
			            newintent.setClass(editInfoActivity, SettingsActivity.class);
			            editInfoActivity.startActivity(newintent);
			            editInfoActivity.finish();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};
	/**
     * 将二进制字节数组转换为十六进制字符串
     * @param arrB
     * @return
     */
	 public static String byteArr2HexStr(byte[] arrB)
	    {
	        if (arrB == null) { return ""; }
	        int iLen = arrB.length;

	        StringBuffer sb = new StringBuffer(iLen * 2);
	        for (int i = 0; i < iLen; i++)
	        {
	            int intTmp = arrB[i];

	            while (intTmp < 0)
	            {
	                intTmp += 256;
	            }

	            if (intTmp < 16)
	            {
	                sb.append("0");
	            }
	            sb.append(Integer.toString(intTmp, 16));
	        }
	        return sb.toString();
	    }

}