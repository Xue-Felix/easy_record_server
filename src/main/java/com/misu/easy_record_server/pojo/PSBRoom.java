package com.misu.easy_record_server.pojo;

public class PSBRoom {

    private String fjh; // 房间号

    private String cws; // 床位数

    private String lc; // 楼层

    private String lcxh; // 楼层序号

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getCws() {
        return cws;
    }

    public void setCws(String cws) {
        this.cws = cws;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public String getLcxh() {
        return lcxh;
    }

    public void setLcxh(String lcxh) {
        this.lcxh = lcxh;
    }
}
