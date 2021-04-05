package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMode_Model {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("acctName")
    @Expose
    private String acctName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }
}
