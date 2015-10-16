package com.whut.data.model;

public class CommentModel {
	private String cId;
	private String uId;
	private String dId;
	private int uPic;
	private String comment;
	private String data;
	private String uName;
	
	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public CommentModel(String cId,String uId,String dId,int uPic,String comment,String data,String uname){
		this.cId = cId;
		this.uId = uId;
		this.dId = dId;
		this.uPic = uPic;
		this.data = data;
		this.uName = uname;
		this.comment = comment;
	}
	
	public CommentModel() {
		// TODO Auto-generated constructor stub
	}

	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getdId() {
		return dId;
	}
	public void setdId(String dId) {
		this.dId = dId;
	}

	public int getuPic() {
		return uPic;
	}

	public void setuPic(int uPic) {
		this.uPic = uPic;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	

}
