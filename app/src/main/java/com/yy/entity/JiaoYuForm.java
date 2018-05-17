package com.yy.entity;

import java.util.List;


public class JiaoYuForm {
	private String oname,otype,texts;
	private List<String[]> ovalues;
	
	public JiaoYuForm() {
		super();
	}
	

	public String getTexts() {
		return texts;
	}


	public void setTexts(String texts) {
		this.texts = texts;
	}


	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public String getOtype() {
		return otype;
	}
	public void setOtype(String otype) {
		this.otype = otype;
	}
	
	public List<String[]> getOvalues() {
		return ovalues;
	}


	public void setOvalues(List<String[]> ovalues) {
		this.ovalues = ovalues;
	}


	@Override
	public String toString() {
		return "JiaoYuForm [oname=" + oname + ", otype=" + otype + ", ovalues="
				+ ovalues + "]"+"texts"+texts;
	}
	
}
