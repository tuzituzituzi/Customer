package com.whut.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class GoodsModel implements Serializable {
	private String gid; // 商品id号
	private String title; // 商品名
	private String desc; // 商品描述
	private String thumbnailUrl; // 缩略图地址
	private int purchaseCount; // 销量
	private double originPrice; // 原价
	private double currentPrice; // 现价

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
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

	public int getPurchaseCount() {
		return purchaseCount;
	}

	public void setPurchaseCount(int purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	public double getOriginPrice() {
		return new BigDecimal(originPrice)
				.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setOriginPrice(double originPrice) {
		this.originPrice = originPrice;
	}

	public double getCurrentPrice() {
		return new BigDecimal(currentPrice).setScale(1,
				BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

}