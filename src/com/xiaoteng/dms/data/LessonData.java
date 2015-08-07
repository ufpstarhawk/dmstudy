package com.xiaoteng.dms.data;
/***
 * 课程首页
 * @author zach
 *
 */
public class LessonData {
	private int lessonid;//课程系统唯一编号
	private String lessonName;//课程名称
	private String imgurl;//课程图片URL
	private int score;//评分
	private int hit;//点击数量
	private int price;//价格
	
	public int getLessonid() {
		return lessonid;
	}
	public void setLessonid(int lessonid) {
		this.lessonid = lessonid;
	}
	public String getLessonName() {
		return lessonName;
	}
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "lessonData [lessonid=" + lessonid + ", lessonName="
				+ lessonName + ", imgurl=" + imgurl + ", score=" + score
				+ ", hit=" + hit + ", price=" + price + "]";
	}
	
	
}
