package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CustomerBill_Model implements Serializable {

    @SerializedName("acct")
    @Expose
    private List<Acct> acct = null;
    @SerializedName("acctBal")
    @Expose
    private Integer acctBal;
    @SerializedName("custAdvAmt")
    @Expose
    private Integer custAdvAmt;

    public List<Acct> getAcct() {
        return acct;
    }

    public void setAcct(List<Acct> acct) {
        this.acct = acct;
    }

    public Integer getAcctBal() {
        return acctBal;
    }

    public void setAcctBal(Integer acctBal) {
        this.acctBal = acctBal;
    }

    public Integer getCustAdvAmt() {
        return custAdvAmt;
    }

    public void setCustAdvAmt(Integer custAdvAmt) {
        this.custAdvAmt = custAdvAmt;
    }


    public class Acct implements Serializable {

        @SerializedName("salesCrId")
        @Expose
        private Integer salesCrId;
        @SerializedName("tranNo")
        @Expose
        private String tranNo;
        @SerializedName("tranDate")
        @Expose
        private String tranDate;
        @SerializedName("salesAmt")
        @Expose
        private Integer salesAmt;
        @SerializedName("recvAmt")
        @Expose
        private Integer recvAmt;
        @SerializedName("rndAmt")
        @Expose
        private Integer rndAmt;

        public Integer getSalesCrId() {
            return salesCrId;
        }

        public void setSalesCrId(Integer salesCrId) {
            this.salesCrId = salesCrId;
        }

        public String getTranNo() {
            return tranNo;
        }

        public void setTranNo(String tranNo) {
            this.tranNo = tranNo;
        }

        public String getTranDate() {
            return tranDate;
        }

        public void setTranDate(String tranDate) {
            this.tranDate = tranDate;
        }

        public Integer getSalesAmt() {
            return salesAmt;
        }

        public void setSalesAmt(Integer salesAmt) {
            this.salesAmt = salesAmt;
        }

        public Integer getRecvAmt() {
            return recvAmt;
        }

        public void setRecvAmt(Integer recvAmt) {
            this.recvAmt = recvAmt;
        }

        public Integer getRndAmt() {
            return rndAmt;
        }

        public void setRndAmt(Integer rndAmt) {
            this.rndAmt = rndAmt;
        }

    }


}
