package com.whut.data.model;

public class UserModel {
	private String uId;
	private String uName;
	private String uPicUrl;
	private String uPassword;
	private String uToken;
	
	public String getuToken() {
		return uToken;
	}
	public void setuToken(String uToken) {
		this.uToken = uToken;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPicUrl() {
		return uPicUrl;
	}
	public void setuPicUrl(String uPicUrl) {
		this.uPicUrl = uPicUrl;
	}
	public String getuPassword() {
		return uPassword;
	}
	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}
	

}
