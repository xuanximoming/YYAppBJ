package com.yy.entity;

import java.util.List;

public class BigType {
	private String id;
	private String name;
	private boolean checked;
	private List<LittleType> littletypelist;

	public BigType() {
		super();
	}

	public BigType(String id, String name, boolean checked,
			List<LittleType> littletypelist) {
		super();
		this.id = id;
		this.name = name;
		this.checked = checked;
		this.littletypelist = littletypelist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<LittleType> getLittletypelist() {
		return littletypelist;
	}

	public void setLittletypelist(List<LittleType> littletypelist) {
		this.littletypelist = littletypelist;
	}

	@Override
	public String toString() {
		return "BigType [id=" + id + ", name=" + name + ", checked=" + checked
				+ ", littletypelist=" + littletypelist + "]";
	}

}
