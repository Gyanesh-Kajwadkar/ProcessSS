package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchUser_Model {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("brnchName")
    @Expose
    private String brnchName;
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
    @SerializedName("phNo")
    @Expose
    private String phNo;
    @SerializedName("mobNo")
    @Expose
    private String mobNo;
    @SerializedName("showName")
    @Expose
    private String showName;
    @SerializedName("gstin")
    @Expose
    private String gstin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrnchName() {
        return brnchName;
    }

    public void setBrnchName(String brnchName) {
        this.brnchName = brnchName;
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

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }
}
