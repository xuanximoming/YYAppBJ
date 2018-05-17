package com.yy.entity;

import java.util.List;

public class JianYan {
	// String BRID, ZYID, JY_ID, JY_BIAOBEN;
	/*
	 * 检验ID 检验标本 临床诊断 检验日期 检验结果列表 项目名称 结果 标准值
	 */
	String JY_ID, JY_BIAOBEN, JY_ZHENDUAN, JY_DATE;
	List JY_RESULT ;

	public JianYan() {
		super();
	}

	public String getJY_ID() {
		return JY_ID;
	}

	public void setJY_ID(String jY_ID) {
		JY_ID = jY_ID;
	}

	public String getJY_BIAOBEN() {
		return JY_BIAOBEN;
	}

	public void setJY_BIAOBEN(String jY_BIAOBEN) {
		JY_BIAOBEN = jY_BIAOBEN;
	}

	public String getJY_ZHENDUAN() {
		return JY_ZHENDUAN;
	}

	public void setJY_ZHENDUAN(String jY_ZHENDUAN) {
		JY_ZHENDUAN = jY_ZHENDUAN;
	}

	public String getJY_DATE() {
		return JY_DATE;
	}

	public void setJY_DATE(String jY_DATE) {
		JY_DATE = jY_DATE;
	}


	public List getJY_RESULT() {
		return JY_RESULT;
	}

	public void setJY_RESULT(List jY_RESULT) {
		JY_RESULT = jY_RESULT;
	}


	@Override
	public String toString() {
		return "JianYan [JY_ID=" + JY_ID + ", JY_BIAOBEN=" + JY_BIAOBEN
				+ ", JY_ZHENDUAN=" + JY_ZHENDUAN + ", JY_DATE=" + JY_DATE
				+ "]";
	}

}
