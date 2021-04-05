package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OfflineSales_Model implements Serializable {

    String userName,userAddress,userMobile,totalAmount;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    private ArrayList<BrandOffline_Model> salesDet = null;

    public List<BrandOffline_Model> getSalesDet() {
        return salesDet;
    }

    public void setSalesDet(ArrayList<BrandOffline_Model> salesDet) {
        this.salesDet = salesDet;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public static class BrandOffline_Model implements Serializable
    { String freetv,stock,brandName;
    boolean setOrNot;
    boolean position;
    int basic,cart;
    int quantity;

    public int getBasic() {
        return basic;
    }

    public void setBasic(int basic) {
        this.basic = basic;
    }

    public int getCart() {
        return cart;
    }

    public void setCart(int cart) {
        this.cart = cart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFreetv() {
        return freetv;
    }

    public void setFreetv(String freetv) {
        this.freetv = freetv;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public boolean isSetOrNot() {
        return setOrNot;
    }

    public void setSetOrNot(boolean setOrNot) {
        this.setOrNot = setOrNot;
    }

    public boolean getPosition() {
        return position;
    }
    public void setPosition(boolean position) {
        this.position = position;
    }

    public Float getrTSWT() {
        return rTSWT;
    }

    public void setrTSWT(Float rTSWT) {
        this.rTSWT = rTSWT;
    }

    public Float getwHSWT() {
        return wHSWT;
    }

    public void setwHSWT(Float wHSWT) {
        this.wHSWT = wHSWT;
    }

    public Float getdTSWT() {
        return dTSWT;
    }

    public void setdTSWT(Float dTSWT) {
        this.dTSWT = dTSWT;
    }

    public Float geteXSWT() {
        return eXSWT;
    }

    public void seteXSWT(Float eXSWT) {
        this.eXSWT = eXSWT;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("itmCd")
    @Expose
    private String itmCd;
    @SerializedName("itmName")
    @Expose
    private String itmName;
    @SerializedName("itmBarCd")
    @Expose
    private String itmBarCd;
    @SerializedName("catgry")
    @Expose
    private String catgry;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("RTSWT")
    @Expose
    private Float rTSWT;
    @SerializedName("WHSWT")
    @Expose
    private Float wHSWT;
    @SerializedName("DTSWT")
    @Expose
    private Float dTSWT;
    @SerializedName("EXSWT")
    @Expose
    private Float eXSWT;
    @SerializedName("avilStk")
    @Expose
    private Float avilStk;
    @SerializedName("taxId")
    @Expose
    private Float taxId;
    @SerializedName("taxPerc")
    @Expose
    private Float taxPerc;
    @SerializedName("mrp")
    @Expose
    private Float mrp;

    @SerializedName("minLevl")
    @Expose
    private Integer minLevl;


    public Integer getMinLevl() {
        return minLevl;
    }

    public void setMinLevl(Integer minLevl) {
        this.minLevl = minLevl;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItmCd() {
        return itmCd;
    }

    public void setItmCd(String itmCd) {
        this.itmCd = itmCd;
    }

    public String getItmName() {
        return itmName;
    }

    public void setItmName(String itmName) {
        this.itmName = itmName;
    }

    public String getItmBarCd() {
        return itmBarCd;
    }

    public void setItmBarCd(String itmBarCd) {
        this.itmBarCd = itmBarCd;
    }

    public String getCatgry() {
        return catgry;
    }

    public void setCatgry(String catgry) {
        this.catgry = catgry;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Float getRTSWT() {
        return rTSWT;
    }

    public void setRTSWT(Float rTSWT) {
        this.rTSWT = rTSWT;
    }

    public Float getWHSWT() {
        return wHSWT;
    }

    public void setWHSWT(Float wHSWT) {
        this.wHSWT = wHSWT;
    }

    public Float getDTSWT() {
        return dTSWT;
    }

    public void setDTSWT(Float dTSWT) {
        this.dTSWT = dTSWT;
    }

    public Float getEXSWT() {
        return eXSWT;
    }

    public void setEXSWT(Float eXSWT) {
        this.eXSWT = eXSWT;
    }

    public Float getAvilStk() {
        return avilStk;
    }

    public void setAvilStk(Float avilStk) {
        this.avilStk = avilStk;
    }

    public Float getTaxId() {
        return taxId;
    }

    public void setTaxId(Float taxId) {
        this.taxId = taxId;
    }

    public Float getTaxPerc() {
        return taxPerc;
    }

    public void setTaxPerc(Float taxPerc) {
        this.taxPerc = taxPerc;
    }

    public Float getMrp() {
        return mrp;
    }

    public void setMrp(Float mrp) {
        this.mrp = mrp;
    }

}

}
