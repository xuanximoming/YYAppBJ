package com.yy.entity;

public class Yangben {
    String BRID, ZYID, TEST_NO_SRC, YBNAME, CJNAME, CJTIME, TESTNAME;
    private int CAIOK;

    public int getCAIOK() {
        return CAIOK;
    }

    public void setCAIOK(int cAIOK) {
        CAIOK = cAIOK;
    }

    public Yangben() {
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

    public String getTEST_NO_SRC() {
        return TEST_NO_SRC;
    }

    public void setTEST_NO_SRC(String tEST_NO_SRC) {
        TEST_NO_SRC = tEST_NO_SRC;
    }

    public String getYBNAME() {
        return YBNAME;
    }

    public void setYBNAME(String yBNAME) {
        YBNAME = yBNAME;
    }

    public String getCJNAME() {
        return CJNAME;
    }

    public void setCJNAME(String cJNAME) {
        CJNAME = cJNAME;
    }

    public String getCJTIME() {
        return CJTIME;
    }

    public void setCJTIME(String cJTIME) {
        CJTIME = cJTIME;
    }

    public String getTESTNAME() {
        return TESTNAME;
    }

    public void setTESTNAME(String TESTNAME) {
        this.TESTNAME = TESTNAME;
    }

    @Override
    public String toString() {
        return "Yangben [BRID=" + BRID + ", ZYID=" + ZYID + ", TEST_NO_SRC="
                + TEST_NO_SRC + ", YBNAME=" + YBNAME + ", CJNAME=" + CJNAME
                + ", CJTIME=" + CJTIME + ", CAIOK=" + CAIOK + ",TESTNAME=" + TESTNAME + "]";
    }

}
