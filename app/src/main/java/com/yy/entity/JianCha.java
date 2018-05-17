package com.yy.entity;

public class JianCha {
String BRID,ZYID,JC_ID,JC_BIGC,JC_SMC,JC_MEMO,JC_DATE,JC_DOCT;

public JianCha() {
	super();
}

public String getBRID() {
	return BRID;
}

public void setBRID(String bRID) {
	BRID = bRID;
}

public String getZYID() {
	return ZYID;
}

public void setZYID(String zYID) {
	ZYID = zYID;
}

public String getJC_ID() {
	return JC_ID;
}

public void setJC_ID(String jC_ID) {
	JC_ID = jC_ID;
}

public String getJC_BIGC() {
	return JC_BIGC;
}

public void setJC_BIGC(String jC_BIGC) {
	JC_BIGC = jC_BIGC;
}

public String getJC_SMC() {
	return JC_SMC;
}

public void setJC_SMC(String jC_SMC) {
	JC_SMC = jC_SMC;
}

public String getJC_MEMO() {
	return JC_MEMO;
}

public void setJC_MEMO(String jC_MEMO) {
	JC_MEMO = jC_MEMO;
}

public String getJC_DATE() {
	return JC_DATE;
}

public void setJC_DATE(String jC_DATE) {
	JC_DATE = jC_DATE;
}

public String getJC_DOCT() {
	return JC_DOCT;
}

public void setJC_DOCT(String jC_DOCT) {
	JC_DOCT = jC_DOCT;
}

@Override
public String toString() {
	return "JianCha [BRID=" + BRID + ", ZYID=" + ZYID + ", JC_ID=" + JC_ID
			+ ", JC_BIGC=" + JC_BIGC + ", JC_SMC=" + JC_SMC + ", JC_MEMO="
			+ JC_MEMO + ", JC_DATE=" + JC_DATE + ", JC_DOCT=" + JC_DOCT + "]";
}

}
