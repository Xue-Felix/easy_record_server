package com.misu.easy_record_server.pojo;

public class PSBHotel {
    private String name; // 旅馆名称
    private String tel; // 电话号码
    private String address; // 地址
    private String fjid; // 分局ID
    private String fjname; // 分局名称
    private String pcsid; // 派出所ID
    private String pcsname; // 派出所名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFjid() {
        return fjid;
    }

    public void setFjid(String fjid) {
        this.fjid = fjid;
    }

    public String getFjname() {
        return fjname;
    }

    public void setFjname(String fjname) {
        this.fjname = fjname;
    }

    public String getPcsid() {
        return pcsid;
    }

    public void setPcsid(String pcsid) {
        this.pcsid = pcsid;
    }

    public String getPcsname() {
        return pcsname;
    }

    public void setPcsname(String pcsname) {
        this.pcsname = pcsname;
    }
}
