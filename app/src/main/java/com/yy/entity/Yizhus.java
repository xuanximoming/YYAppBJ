package com.yy.entity;

import java.util.List;

public class Yizhus {
	int TOP1YZOID;
	String HEDUICODE;
	List<Yizhutype> typelist, cllist;
	List<Yizhu> yizhulist;
	
	
	public Yizhus() {
		super();
	}
	public List<Yizhutype> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<Yizhutype> typelist) {
		this.typelist = typelist;
	}
	public List<Yizhutype> getCllist() {
		return cllist;
	}
	public void setCllist(List<Yizhutype> cllist) {
		this.cllist = cllist;
	}
	public List<Yizhu> getYizhulist() {
		return yizhulist;
	}
	public void setYizhulist(List<Yizhu> yizhulist) {
		this.yizhulist = yizhulist;
	}
	public int getTOP1YZOID() {
		return TOP1YZOID;
	}
	public void setTOP1YZOID(int tOP1YZOID) {
		TOP1YZOID = tOP1YZOID;
	}
	public String getHEDUICODE() {
		return HEDUICODE;
	}
	public void setHEDUICODE(String hEDUICODE) {
		HEDUICODE = hEDUICODE;
	}
	
}
