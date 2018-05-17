package com.yy.entity;

public class Hulilist {
	String RECORDING_DATE, COUNUM;

	public Hulilist() {
		super();
	}

	public String getRECORDING_DATE() {
		return RECORDING_DATE;
	}

	public void setRECORDING_DATE(String rECORDING_DATE) {
		RECORDING_DATE = rECORDING_DATE;
	}

	public String getCOUNUM() {
		return COUNUM;
	}

	public void setCOUNUM(String cOUNUM) {
		COUNUM = cOUNUM;
	}

	@Override
	public String toString() {
		return "Hulilist [RECORDING_DATE=" + RECORDING_DATE + ", COUNUM="
				+ COUNUM + "]";
	}

}
