package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemList_Model implements Serializable {

    String quantity,freetv,stock,brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    int basic,cart;

    public String getFreetv() {
        return freetv;
    }

    public void setFreetv(String freetv) {
        this.freetv = freetv;
    }

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
    private Object itmBarCd;
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
    private Integer rTSWT;
    @SerializedName("WHSWT")
    @Expose
    private Integer wHSWT;
    @SerializedName("DTSWT")
    @Expose
    private Integer dTSWT;
    @SerializedName("EXSWT")
    @Expose
    private Integer eXSWT;
    @SerializedName("avilStk")
    @Expose
    private Integer avilStk;
    @SerializedName("taxId")
    @Expose
    private Integer taxId;
    @SerializedName("taxPerc")
    @Expose
    private Integer taxPerc;
    @SerializedName("mrp")
    @Expose
    private Integer mrp;

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

    public Object getItmBarCd() {
        return itmBarCd;
    }

    public void setItmBarCd(Object itmBarCd) {
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

    public Integer getRTSWT() {
        return rTSWT;
    }

    public void setRTSWT(Integer rTSWT) {
        this.rTSWT = rTSWT;
    }

    public Integer getWHSWT() {
        return wHSWT;
    }

    public void setWHSWT(Integer wHSWT) {
        this.wHSWT = wHSWT;
    }

    public Integer getDTSWT() {
        return dTSWT;
    }

    public void setDTSWT(Integer dTSWT) {
        this.dTSWT = dTSWT;
    }

    public Integer getEXSWT() {
        return eXSWT;
    }

    public void setEXSWT(Integer eXSWT) {
        this.eXSWT = eXSWT;
    }

    public Integer getAvilStk() {
        return avilStk;
    }

    public void setAvilStk(Integer avilStk) {
        this.avilStk = avilStk;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public Integer getTaxPerc() {
        return taxPerc;
    }

    public void setTaxPerc(Integer taxPerc) {
        this.taxPerc = taxPerc;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }
}
