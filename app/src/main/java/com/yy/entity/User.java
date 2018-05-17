package com.yy.entity;

public class User {
	private String uid, utname, keshi;
	private int timeout;

	
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUtname() {
		return utname;
	}

	public void setUtname(String utname) {
		this.utname = utname;
	}

	public String getKeshi() {
		return keshi;
	}

	public void setKeshi(String keshi) {
		this.keshi = keshi;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", utname=" + utname + ", keshi=" + keshi
				+ "]";
	}

}
