package com.yy.entity;

import java.io.Serializable;

public class Patient implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name, age, gender, keshi, hulidengji, quhao, chuanghao, time;
	String patient_id, zy_id, patient_birth,zhuyi;
	String ZHENDUAN,FEIBIE,FEIYUE;
	public Patient() {
		super();
	}

	public String getZhuyi() {
		return zhuyi;
	}

	public void setZhuyi(String zhuyi) {
		this.zhuyi = zhuyi;
	}

	public String getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getZy_id() {
		return zy_id;
	}

	public void setZy_id(String zy_id) {
		this.zy_id = zy_id;
	}

	public String getPatient_birth() {
		return patient_birth;
	}

	public void setPatient_birth(String patient_birth) {
		this.patient_birth = patient_birth;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getKeshi() {
		return keshi;
	}

	public void setKeshi(String keshi) {
		this.keshi = keshi;
	}

	public String getHulidengji() {
		return hulidengji;
	}

	public void setHulidengji(String hulidengji) {
		this.hulidengji = hulidengji;
	}

	public String getQuhao() {
		return quhao;
	}

	public void setQuhao(String quhao) {
		this.quhao = quhao;
	}

	public String getChuanghao() {
		return chuanghao;
	}

	public void setChuanghao(String chuanghao) {
		this.chuanghao = chuanghao;
	}

	public String getZHENDUAN() {
		return ZHENDUAN;
	}

	public void setZHENDUAN(String zHENDUAN) {
		ZHENDUAN = zHENDUAN;
	}

	public String getFEIBIE() {
		return FEIBIE;
	}

	public void setFEIBIE(String fEIBIE) {
		FEIBIE = fEIBIE;
	}

	public String getFEIYUE() {
		return FEIYUE;
	}

	public void setFEIYUE(String fEIYUE) {
		FEIYUE = fEIYUE;
	}

	@Override
	public String toString() {
		return "Patient [name=" + name + ", age=" + age + ", gender=" + gender
				+ ", keshi=" + keshi + ", hulidengji=" + hulidengji
				+ ", quhao=" + quhao + ", chuanghao=" + chuanghao + ", time="
				+ time + ", patient_id=" + patient_id + ", zy_id=" + zy_id
				+ ", patient_birth=" + patient_birth + ", zhuyi=" + zhuyi
				+ ", ZHENDUAN=" + ZHENDUAN + ", FEIBIE=" + FEIBIE + ", FEIYUE="
				+ FEIYUE + "]";
	}

}
