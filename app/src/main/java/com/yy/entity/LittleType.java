package com.yy.entity;

public class LittleType {
	private String id;
	private String name;
	private boolean checked;

	public LittleType() {
		super();
	}

	public LittleType(String id, String name, boolean checked) {
		super();
		this.id = id;
		this.name = name;
		this.checked = checked;
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

	@Override
	public String toString() {
		return "LittleType [id=" + id + ", name=" + name + ", checked="
				+ checked + "]";
	}

}
