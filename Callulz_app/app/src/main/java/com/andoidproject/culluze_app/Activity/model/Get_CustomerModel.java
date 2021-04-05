package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Get_CustomerModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("custmrName")
    @Expose
    private String custmrName;
    @SerializedName("acctId")
    @Expose
    private Integer acctId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("mobl")
    @Expose
    private String mobl;
    @SerializedName("gstin")
    @Expose
    private String gstin;
    @SerializedName("custRate")
    @Expose
    private String custRate;
    @SerializedName("avilBal")
    @Expose
    private String avilBal;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("creditLimitFlg")
    @Expose
    private String creditLimitFlg;
    @SerializedName("limitAmt")
    @Expose
    private String limitAmt;
    @SerializedName("limitDays")
    @Expose
    private String limitDays;
    @SerializedName("pendingBillCount")
    @Expose
    private Integer pendingBillCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustmrName() {
        return custmrName;
    }

    public void setCustmrName(String custmrName) {
        this.custmrName = custmrName;
    }

    public Integer getAcctId() {
        return acctId;
    }

    public void setAcctId(Integer acctId) {
        this.acctId = acctId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobl() {
        return mobl;
    }

    public void setMobl(String mobl) {
        this.mobl = mobl;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getCustRate() {
        return custRate;
    }

    public void setCustRate(String custRate) {
        this.custRate = custRate;
    }

    public String getAvilBal() {
        return avilBal;
    }

    public void setAvilBal(String avilBal) {
        this.avilBal = avilBal;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCreditLimitFlg() {
        return creditLimitFlg;
    }

    public void setCreditLimitFlg(String creditLimitFlg) {
        this.creditLimitFlg = creditLimitFlg;
    }

    public String getLimitAmt() {
        return limitAmt;
    }

    public void setLimitAmt(String limitAmt) {
        this.limitAmt = limitAmt;
    }

    public String getLimitDays() {
        return limitDays;
    }

    public void setLimitDays(String limitDays) {
        this.limitDays = limitDays;
    }

    public Integer getPendingBillCount() {
        return pendingBillCount;
    }

    public void setPendingBillCount(Integer pendingBillCount) {
        this.pendingBillCount = pendingBillCount;
    }

}
