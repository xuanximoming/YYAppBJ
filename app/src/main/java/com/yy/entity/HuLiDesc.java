package com.yy.entity;

public class HuLiDesc {
	/*
	 * HL_ITEM = hl_item VITAL_CODE = hlid HL_VALUE = hl_value hl_type = hl_type
	 */
	String HL_ITEM, UNITS, CLASS_CODE, VITAL_CODE, HL_VALUE;
	String hl_type;
	String HL_TIME, ALERTMSG;
	int MIN_VALUE, MAX_VALUE;

    public String NEW_CLASS_VITAL_CODE;//新增属性，用于添加护理表单所用
    public String WARD_CODE;//新增属性，用于添加护理表单所用

	public String getHl_type() {
		return hl_type;
	}

	public void setHl_type(String hl_type) {
		this.hl_type = hl_type;
	}

	public HuLiDesc() {
		super();
	}

	public String getHL_ITEM() {
		return HL_ITEM;
	}

	public void setHL_ITEM(String hL_ITEM) {
		HL_ITEM = hL_ITEM;
	}

	public String getUNITS() {
		return UNITS;
	}

	public void setUNITS(String uNITS) {
		UNITS = uNITS;
	}

	public String getCLASS_CODE() {
		return CLASS_CODE;
	}

	public void setCLASS_CODE(String cLASS_CODE) {
		CLASS_CODE = cLASS_CODE;
	}

	public String getVITAL_CODE() {
		return VITAL_CODE;
	}

	public void setVITAL_CODE(String vITAL_CODE) {
		VITAL_CODE = vITAL_CODE;
	}

	public String getHL_VALUE() {
		return HL_VALUE;
	}

	public void setHL_VALUE(String hL_VALUE) {
		HL_VALUE = hL_VALUE;
	}

	public String getHL_TIME() {
		return HL_TIME;
	}

	public void setHL_TIME(String hL_TIME) {
		HL_TIME = hL_TIME;
	}


	public int getMIN_VALUE() {
		return MIN_VALUE;
	}

	public void setMIN_VALUE(int mIN_VALUE) {
		MIN_VALUE = mIN_VALUE;
	}

	public int getMAX_VALUE() {
		return MAX_VALUE;
	}

	public void setMAX_VALUE(int mAX_VALUE) {
		MAX_VALUE = mAX_VALUE;
	}

	public String getALERTMSG() {
		return ALERTMSG;
	}

	public void setALERTMSG(String aLERTMSG) {
		ALERTMSG = aLERTMSG;
	}

	@Override
	public String toString() {
		return "HuLiDesc [HL_ITEM=" + HL_ITEM + ", UNITS=" + UNITS
				+ ", CLASS_CODE=" + CLASS_CODE + ", VITAL_CODE=" + VITAL_CODE
				+ ", HL_VALUE=" + HL_VALUE + ", hl_type=" + hl_type
				+ ", HL_TIME=" + HL_TIME + ", MIN_VALUE=" + MIN_VALUE
				+ ", MAX_VALUE=" + MAX_VALUE + ", ALERTMSG=" + ALERTMSG + "]";
	}

}
