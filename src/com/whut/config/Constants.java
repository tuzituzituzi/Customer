package com.whut.config;

public class Constants {
//	public static final String ROOT_HTTP = "http://115.28.9.186:8899/store/service/201/node-tair-web";
	public static final String ROOT_HTTP = "http://219.153.20.141:8899/store/service/201/node-tair-web";
	public static final String GET_ADS_WALL = ROOT_HTTP + "/app/mall/AdsWall";// /app/mall/AdsWall
	public static final String GET_GOODS_LIST = ROOT_HTTP
			+ "/app/mall/GoodsList";// /app/mall/GoodsList
	public static final String GET_SHOP_LIST = ROOT_HTTP + "/app/mall/ShopList";// ShopList
	public static final String GET_GOODS_DETAIL = ROOT_HTTP
			+ "/app/mall/GoodsDetail";// gId=1000001
	public static final String GET_SHOP_DETAIL = ROOT_HTTP
			+ "/app/mall/ShopDetail";
	public static final String GET_COUPON_LIST = ROOT_HTTP
			+ "/app/mall/CouponList";
	public static final String GET_COUPON_DETAIL = ROOT_HTTP
			+ "/app/mall/CouponDetail";// cid=100001

	public static final String GetDownLoadURL = "http://www.pgyer.com/apiv1/app/install?aId=a102103b04ce4fcff6dc0f06d9a3d9d3&_api_key=46ddf500711c4cb14441e908fe2bc002";
	public static final String GetAPPID = "a102103b04ce4fcff6dc0f06d9a3d9d3";
	
	public static final String FILE_PATH = "/customer";

	public static final String CAN_RETURN_OVERDATE = "支持过期退款";
	public static final String CANNOT_RETURN_OVERDATE = "不支持过期退款";
	public static final String CAN_RETURN_ANYTIME = "支持随时退款";
	public static final String CANNOT_RETURN_ANYTIME = "不支持随时退款";
}
