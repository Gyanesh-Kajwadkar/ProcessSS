package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitCheckInModel {
    @SerializedName("usrId")
    @Expose
    private Integer usrId;
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("chkFlg")
    @Expose
    private String chkFlg;
    @SerializedName("resheDt")
    @Expose
    private String resheDt;
    @SerializedName("resheTim")
    @Expose
    private String resheTim;

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getChkFlg() {
        return chkFlg;
    }

    public void setChkFlg(String chkFlg) {
        this.chkFlg = chkFlg;
    }

    public String getResheDt() {
        return resheDt;
    }

    public void setResheDt(String resheDt) {
        this.resheDt = resheDt;
    }

    public String getResheTim() {
        return resheTim;
    }

    public void setResheTim(String resheTim) {
        this.resheTim = resheTim;
    }
}
