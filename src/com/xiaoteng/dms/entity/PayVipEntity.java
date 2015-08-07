package com.xiaoteng.dms.entity;

public class PayVipEntity {
	//会员期限
	private static int month;
	//支付渠道
	private static String payChl;
	//当前时间
	private static long timestamp;
	//设备类型
	private static String device;
	//服务端订单号
	private static String orderno;
	//需要透传给第三方支付平台的字符串，具体含义区分于第三方平台
	private static String tranStr;
	//支付金额
	private static int price;
	//通知地址
	private static String notifyUrl;
	
	public static int getMonth() {
		return month;
	}
	public static void setMonth(int month) {
		PayVipEntity.month = month;
	}
	public static String getPayChl() {
		return payChl;
	}
	public static void setPayChl(String payChl) {
		PayVipEntity.payChl = payChl;
	}
	public static long getTimestamp() {
		return timestamp;
	}
	public static void setTimestamp(long timestamp) {
		PayVipEntity.timestamp = timestamp;
	}
	public static String getDevice() {
		return device;
	}
	public static void setDevice(String device) {
		PayVipEntity.device = device;
	}
	public static String getOrderno() {
		return orderno;
	}
	public static void setOrderno(String orderno) {
		PayVipEntity.orderno = orderno;
	}
	public static String getTranStr() {
		return tranStr;
	}
	public static void setTranStr(String tranStr) {
		PayVipEntity.tranStr = tranStr;
	}
	public static int getPrice() {
		return price;
	}
	public static void setPrice(int price) {
		PayVipEntity.price = price;
	}
	public static String getNotifyUrl() {
		return notifyUrl;
	}
	public static void setNotifyUrl(String notifyUrl) {
		PayVipEntity.notifyUrl = notifyUrl;
	}
	@Override
	public String toString() {
		return "PayVipEntity [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
