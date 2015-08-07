package com.xiaoteng.dms.dialog;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiaoteng.dms.R;
import com.xiaoteng.dms.entity.PayVipEntity;
import com.xiaoteng.dms.entity.UpdataUserEntity;
import com.xiaoteng.dms.http.Http;
/**
 * 支付方式
 * @author zach
 *
 */
public class PayDialog extends Dialog {

    public PayDialog(Context context) {
        super(context);
    }

    public PayDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class PayDialogBuilder implements android.view.View.OnClickListener {
        private Context context;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
		private RelativeLayout rl_weixin;
		private RelativeLayout rl_zhifubao;
		private RelativeLayout rl_phonezhifu;
		private LinearLayout ll_confirmpay;
		
        public PayDialogBuilder(Context context) {
            this.context = context;
        }

        public PayDialogBuilder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public PayDialog create() {
        	
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final PayDialog dialog = new PayDialog(context);
            View layout = inflater.inflate(R.layout.dialog_pay, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            rl_weixin = (RelativeLayout) dialog.findViewById(R.id.weixin_rl);
            rl_zhifubao = (RelativeLayout) dialog.findViewById(R.id.zhifubao_rl);
            ll_confirmpay = (LinearLayout) dialog.findViewById(R.id.confirmpay_ll);
            rl_weixin.setOnClickListener(this);
            rl_zhifubao.setOnClickListener(this);
            ll_confirmpay.setOnClickListener(this);
            dialog.setContentView(layout);
            dialog.setTitle("请选择支付方式");
            return dialog;
        }
        
        //渠道
        String paychl = "";
		private ArrayList<NameValuePair> paramsPlayVip;
		private String params;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.weixin_rl:
				rl_weixin.setBackgroundResource(R.drawable.site_pay);
				rl_zhifubao.setBackgroundResource(R.drawable.pay_bg);
				paychl = "SDK_WEIXIN";
				break;
			case R.id.zhifubao_rl:
				rl_weixin.setBackgroundResource(R.drawable.pay_bg);
				rl_zhifubao.setBackgroundResource(R.drawable.site_pay);
				paychl = "SDK_ALI";
				break;
			case R.id.confirmpay_ll:
				if(!paychl.equals("")){
					String urlpay = "http://www.linhoo.com.cn/educate/pay/buyvip.api";
					// 购买会员
					// 带参
					paramsPlayVip = new ArrayList<NameValuePair>();
					paramsPlayVip.add(new BasicNameValuePair("userid", UpdataUserEntity.getUserid()+""));
					paramsPlayVip.add(new BasicNameValuePair("token", UpdataUserEntity.getToken()));
					paramsPlayVip.add(new BasicNameValuePair("month", PayVipEntity.getMonth()+""));
					paramsPlayVip.add(new BasicNameValuePair("paychl", paychl));
					paramsPlayVip.add(new BasicNameValuePair("timestamp", System.currentTimeMillis()+""));
					paramsPlayVip.add(new BasicNameValuePair("device", "ANDROID"));
					params = URLEncodedUtils.format(paramsPlayVip, "UTF-8");
					Http.yanzheng(urlpay, params, 8);
				}
				break;
			}
		}
		
    }
    
 
}