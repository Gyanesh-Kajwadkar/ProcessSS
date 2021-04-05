package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCustomer_Model {
    @SerializedName("usrId")
    @Expose
    private Integer usrId;
    @SerializedName("custName")
    @Expose
    private String custName;
    @SerializedName("mobNo")
    @Expose
    private String mobNo;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("locLongt")
    @Expose
    private String locLongt;
    @SerializedName("locLatt")
    @Expose
    private String locLatt;

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getLocLongt() {
        return locLongt;
    }

    public void setLocLongt(String locLongt) {
        this.locLongt = locLongt;
    }

    public String getLocLatt() {
        return locLatt;
    }

    public void setLocLatt(String locLatt) {
        this.locLatt = locLatt;
    }
}
