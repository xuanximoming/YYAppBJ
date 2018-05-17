package com.yy.entity;

public class JiaoJieBanDes {
	/*
	 * status INT 获取信息成功 jb_date String 交班时间 jb_memos String 交班内容
	 */
	String INT, jb_date, jb_memos;

	public String getINT() {
		return INT;
	}

	public void setINT(String iNT) {
		INT = iNT;
	}

	public String getJb_date() {
		return jb_date;
	}

	public void setJb_date(String jb_date) {
		this.jb_date = jb_date;
	}

	public String getJb_memos() {
		return jb_memos;
	}

	public void setJb_memos(String jb_memos) {
		this.jb_memos = jb_memos;
	}
	

}
