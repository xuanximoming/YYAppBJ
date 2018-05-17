package com.yy.entity;

public class ShouShu {
	String BRID, ZYID, SHOUSHU_ID, SHOUSHU_DATE,SHOUSHU_YISHI,SHOUSHU_NAME,SHOUSHU_BUWEI;

	public ShouShu() {
		super();
	}

	public String getBRID() {
		return BRID;
	}
	
	public String getSHOUSHU_BUWEI() {
		return SHOUSHU_BUWEI;
	}

	public void setSHOUSHU_BUWEI(String sHOUSHU_BUWEI) {
		SHOUSHU_BUWEI = sHOUSHU_BUWEI;
	}

	public void setBRID(String bRID) {
		BRID = bRID;
	}

	public String getZYID() {
		return ZYID;
	}

	public void setZYID(String zYID) {
		ZYID = zYID;
	}

	public String getSHOUSHU_ID() {
		return SHOUSHU_ID;
	}

	public void setSHOUSHU_ID(String sHOUSHU_ID) {
		SHOUSHU_ID = sHOUSHU_ID;
	}

	public String getSHOUSHU_DATE() {
		return SHOUSHU_DATE;
	}

	public void setSHOUSHU_DATE(String sHOUSHU_DATE) {
		SHOUSHU_DATE = sHOUSHU_DATE;
	}

	public String getSHOUSHU_YISHI() {
		return SHOUSHU_YISHI;
	}

	public void setSHOUSHU_YISHI(String sHOUSHU_YISHI) {
		SHOUSHU_YISHI = sHOUSHU_YISHI;
	}

	public String getSHOUSHU_NAME() {
		return SHOUSHU_NAME;
	}

	public void setSHOUSHU_NAME(String sHOUSHU_NAME) {
		SHOUSHU_NAME = sHOUSHU_NAME;
	}

	@Override
	public String toString() {
		return "ShouShu [BRID=" + BRID + ", ZYID=" + ZYID + ", SHOUSHU_ID="
				+ SHOUSHU_ID + ", SHOUSHU_DATE=" + SHOUSHU_DATE
				+ ", SHOUSHU_YISHI=" + SHOUSHU_YISHI + ", SHOUSHU_NAME="
				+ SHOUSHU_NAME + "]";
	}

}
