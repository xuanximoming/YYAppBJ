package com.yy.entity;


public class Yizhu {
	String BRID, ZYID, YZ_ITEM, YZ_JINUM, YZ_DANWEI, ZX_TIME, ZX_MEMO, YZ_TYPE,
			YZ_CHLIN,ORDER_CLASS_NAME,YZOID
			,TOP1YZOID,
			HEDUICODE,
			YZ_COLOR;
	

	public String getTOP1YZOID() {
		return TOP1YZOID;
	}

	public void setTOP1YZOID(String tOP1YZOID) {
		TOP1YZOID = tOP1YZOID;
	}

	public String getHEDUICODE() {
		return HEDUICODE;
	}

	public void setHEDUICODE(String hEDUICODE) {
		HEDUICODE = hEDUICODE;
	}

	public String getYZ_COLOR() {
		return YZ_COLOR;
	}

	public void setYZ_COLOR(String yZ_COLOR) {
		YZ_COLOR = yZ_COLOR;
	}

	public Yizhu() {
		super();
	}

	public String getZX_MEMO() {
		return ZX_MEMO;
	}

	public void setZX_MEMO(String zX_MEMO) {
		ZX_MEMO = zX_MEMO;
	}

	public String getYZ_TYPE() {
		return YZ_TYPE;
	}

	public void setYZ_TYPE(String yZ_TYPE) {
		YZ_TYPE = yZ_TYPE;
	}

	public String getYZ_CHLIN() {
		return YZ_CHLIN;
	}

	public void setYZ_CHLIN(String yZ_CHLIN) {
		YZ_CHLIN = yZ_CHLIN;
	}

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

	public String getYZ_ITEM() {
		return YZ_ITEM;
	}

	public void setYZ_ITEM(String yZ_ITEM) {
		YZ_ITEM = yZ_ITEM;
	}

	public String getYZ_JINUM() {
		return YZ_JINUM;
	}

	public void setYZ_JINUM(String yZ_JINUM) {
		YZ_JINUM = yZ_JINUM;
	}

	public String getYZ_DANWEI() {
		return YZ_DANWEI;
	}

	public void setYZ_DANWEI(String yZ_DANWEI) {
		YZ_DANWEI = yZ_DANWEI;
	}

	public String getZX_TIME() {
		return ZX_TIME;
	}

	public void setZX_TIME(String zX_TIME) {
		ZX_TIME = zX_TIME;
	}

	public String getORDER_CLASS_NAME() {
		return ORDER_CLASS_NAME;
	}

	public void setORDER_CLASS_NAME(String oRDER_CLASS_NAME) {
		ORDER_CLASS_NAME = oRDER_CLASS_NAME;
	}

	public String getYZOID() {
		return YZOID;
	}

	public void setYZOID(String yZOID) {
		YZOID = yZOID;
	}

	@Override
	public String toString() {
		return "Yizhu [BRID=" + BRID + ", ZYID=" + ZYID + ", YZ_ITEM="
				+ YZ_ITEM + ", YZ_JINUM=" + YZ_JINUM + ", YZ_DANWEI="
				+ YZ_DANWEI + ", ZX_TIME=" + ZX_TIME + ", ZX_MEMO=" + ZX_MEMO
				+ ", YZ_TYPE=" + YZ_TYPE + ", YZ_CHLIN=" + YZ_CHLIN + "]";
	}

}
