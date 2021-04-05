package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyName_Model {

    @SerializedName("compId")
    @Expose
    private Integer compId;
    @SerializedName("compName")
    @Expose
    private String compName;
    @SerializedName("showName")
    @Expose
    private String showName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("distrct")
    @Expose
    private String distrct;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("pinCd")
    @Expose
    private String pinCd;
    @SerializedName("gstNo")
    @Expose
    private Object gstNo;

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrct() {
        return distrct;
    }

    public void setDistrct(String distrct) {
        this.distrct = distrct;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCd() {
        return pinCd;
    }

    public void setPinCd(String pinCd) {
        this.pinCd = pinCd;
    }

    public Object getGstNo() {
        return gstNo;
    }

    public void setGstNo(Object gstNo) {
        this.gstNo = gstNo;
    }

}
