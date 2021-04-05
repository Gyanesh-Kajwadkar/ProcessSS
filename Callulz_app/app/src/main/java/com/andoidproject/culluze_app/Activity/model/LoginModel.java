package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("results")
    @Expose
    private String results;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("usrName")
    @Expose
    private String usrName;
    @SerializedName("brnchId")
    @Expose
    private Integer brnchId;
    @SerializedName("compId")
    @Expose
    private Integer compId;
    @SerializedName("usrType")
    @Expose
    private String usrType;

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public Integer getBrnchId() {
        return brnchId;
    }

    public void setBrnchId(Integer brnchId) {
        this.brnchId = brnchId;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getUsrType() {
        return usrType;
    }

    public void setUsrType(String usrType) {
        this.usrType = usrType;
    }

}
