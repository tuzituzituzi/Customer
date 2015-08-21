package com.whut.data.model;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class CouponModel implements Serializable {
	private String cid; // 优惠券id
	private String title; // 优惠券标题
	private String desc; // 优惠券描述
	private String thumbnailUrl; // 缩略图地址
	private int isUsed; // 是否使用，0:未使用 1:已使用

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}
}
