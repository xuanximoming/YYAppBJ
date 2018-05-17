package com.yy.entity;

public class SearchForm {
	/*
	 * srhid oname otype ovalues ovalue
	 */
	private String srhid,oname, otype;
	private String[] ovalues;

	public SearchForm() {
		super();
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

	public String[] getOvalues() {
		return ovalues;
	}

	public void setOvalues(String[] ovalues) {
		this.ovalues = ovalues;
	}

	public String getSrhid() {
		return srhid;
	}

	public void setSrhid(String srhid) {
		this.srhid = srhid;
	}

	@Override
	public String toString() {
		return "JiaoYuForm [oname=" + oname + ", otype=" + otype + ", ovalues="
				+ ovalues + "]";
	}

}
