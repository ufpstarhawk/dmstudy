package com.xiaoteng.dms.entity;
/**
 * 登陆
 * @author zach
 *
 */
public class UpdataUserEntity {
	//系统用户唯一标识
	private static int userid;
	//用户昵称
	private static String nickname;
	//用户头像URL
	private static String imgurl;
	//推送别名
	private static String  push_alias;
	//推送标签
	private static String  push_tag;
	//第三方即时聊天账号
	private static String im_username;
	//第三方即时聊天密码
	private static String im_password;
	//会员
	private static String vip_type;
	//会员开始时间
	private static String vip_begin;
	//会员到期时间
	private static String vip_expire;
	//用户身份信息
	private static String token;
	public static int getUserid() {
		return userid;
	}
	public static void setUserid(int userid) {
		UpdataUserEntity.userid = userid;
	}
	public static String getIm_username() {
		return im_username;
	}
	public static void setIm_username(String im_username) {
		UpdataUserEntity.im_username = im_username;
	}
	public static String getIm_password() {
		return im_password;
	}
	public static void setIm_password(String im_password) {
		UpdataUserEntity.im_password = im_password;
	}
	public static String getVip_type() {
		return vip_type;
	}
	public static void setVip_type(String vip_type) {
		UpdataUserEntity.vip_type = vip_type;
	}
	public static String getVip_begin() {
		return vip_begin;
	}
	public static void setVip_begin(String vip_begin) {
		UpdataUserEntity.vip_begin = vip_begin;
	}
	public static String getVip_expire() {
		return vip_expire;
	}
	public static void setVip_expire(String vip_expire) {
		UpdataUserEntity.vip_expire = vip_expire;
	}
	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		UpdataUserEntity.token = token;
	}
	
	public static String getNickname() {
		return nickname;
	}
	public static void setNickname(String nickname) {
		UpdataUserEntity.nickname = nickname;
	}
	
	public static String getImgurl() {
		return imgurl;
	}
	public static void setImgurl(String imgurl) {
		UpdataUserEntity.imgurl = imgurl;
	}
	public static String getPush_alias() {
		return push_alias;
	}
	public static void setPush_alias(String push_alias) {
		UpdataUserEntity.push_alias = push_alias;
	}
	public static String getPush_tag() {
		return push_tag;
	}
	public static void setPush_tag(String push_tag) {
		UpdataUserEntity.push_tag = push_tag;
	}
	
	@Override
	public String toString() {
		return "UpdataUserEntity [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	public UpdataUserEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
