package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Orderlist_Model implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tranNo")
    @Expose
    private String tranNo;
    @SerializedName("totSaleAmt")
    @Expose
    private Double totSaleAmt;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public Double getTotSaleAmt() {
        return totSaleAmt;
    }

    public void setTotSaleAmt(Double totSaleAmt) {
        this.totSaleAmt = totSaleAmt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
