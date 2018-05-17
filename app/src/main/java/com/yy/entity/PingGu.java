package com.yy.entity;

public class PingGu {
	String itemid, itemname, itemtextvalue;
	int selected, itemtype, xiaji,excp;

	public PingGu() {
		super();
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemtextvalue() {
		return itemtextvalue;
	}

	public void setItemtextvalue(String itemtextvalue) {
		this.itemtextvalue = itemtextvalue;
	}

	public int getItemtype() {
		return itemtype;
	}

	public void setItemtype(int itemtype) {
		this.itemtype = itemtype;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public int getXiaji() {
		return xiaji;
	}

	public void setXiaji(int xiaji) {
		this.xiaji = xiaji;
	}

	public int getExcp() {
		return excp;
	}

	public void setExcp(int excp) {
		this.excp = excp;
	}

	@Override
	public String toString() {
		return "PingGu [itemid=" + itemid + ", itemname=" + itemname
				+ ", itemtextvalue=" + itemtextvalue + ", selected=" + selected
				+ ", itemtype=" + itemtype + ", xiaji=" + xiaji + ", excp=" + excp + "]";
	}

}
