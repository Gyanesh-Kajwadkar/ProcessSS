package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandList_Model {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("brandCd")
    @Expose
    private String brandCd;
    @SerializedName("brandName")
    @Expose
    private String brandName;
    @SerializedName("companyName")
    @Expose
    private String companyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandCd() {
        return brandCd;
    }

    public void setBrandCd(String brandCd) {
        this.brandCd = brandCd;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
