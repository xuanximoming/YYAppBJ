package com.yy.entity;

public class JiaoJieBan {
	/*
	 * BRID INT 病人ID ZYID INT 本次住院ID JIAOBAN_DATE String 交班时间
	 */
	String BRID, ZYID, JIAOBAN_DATE;

	public String getBRID() {
		return BRID;
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

	public String getJIAOBAN_DATE() {
		return JIAOBAN_DATE;
	}

	public void setJIAOBAN_DATE(String jIAOBAN_DATE) {
		JIAOBAN_DATE = jIAOBAN_DATE;
	}

}
