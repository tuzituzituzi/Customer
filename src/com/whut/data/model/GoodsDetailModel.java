package com.whut.data.model;

public class GoodsDetailModel {
	private String imageUrl; 		//详情页图片地址
	private int isReturnOverdate;	//是否把持过期退款 0:不支持  1:支持
	private int isReturnAnytime;	//是否支持随时退款
	private String category;		//类别
	private String publishDate;		//发布时间
	private String purchaseDeadline;	//销售截止时间
	private String notice;			//重要通知
	private String buyDetail;		//购买需知
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getIsReturnOverdate() {
		return isReturnOverdate;
	}
	public void setIsReturnOverdate(int isReturnOverdate) {
		this.isReturnOverdate = isReturnOverdate;
	}
	public int getIsReturnAnytime() {
		return isReturnAnytime;
	}
	public void setIsReturnAnytime(int isReturnAnytime) {
		this.isReturnAnytime = isReturnAnytime;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getPurchaseDeadline() {
		return purchaseDeadline;
	}
	public void setPurchaseDeadline(String purchaseDeadline) {
		this.purchaseDeadline = purchaseDeadline;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getBuyDetail() {
		return buyDetail;
	}
	public void setBuyDetail(String buyDetail) {
		this.buyDetail = buyDetail;
	}
}
