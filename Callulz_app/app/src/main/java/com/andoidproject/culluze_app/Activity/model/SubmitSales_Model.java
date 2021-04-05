package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitSales_Model {

    @SerializedName("usrId")
    @Expose
    private Integer usrId;
    @SerializedName("custmrId")
    @Expose
    private Integer custmrId;
    @SerializedName("grandTotAmt")
    @Expose
    private String grandTotAmt;
    @SerializedName("roundingAmt")
    @Expose
    private Integer roundingAmt;
    @SerializedName("tranOverheads")
    @Expose
    private Integer tranOverheads;
    @SerializedName("otherCharge")
    @Expose
    private Integer otherCharge;
    @SerializedName("totSaleAmt")
    @Expose
    private Double totSaleAmt;
    @SerializedName("salesDet")
    @Expose
    private List<SalesDet> salesDet = null;

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public Integer getCustmrId() {
        return custmrId;
    }

    public void setCustmrId(Integer custmrId) {
        this.custmrId = custmrId;
    }

    public String getGrandTotAmt() {
        return grandTotAmt;
    }

    public void setGrandTotAmt(String grandTotAmt) {
        this.grandTotAmt = grandTotAmt;
    }

    public Integer getRoundingAmt() {
        return roundingAmt;
    }

    public void setRoundingAmt(Integer roundingAmt) {
        this.roundingAmt = roundingAmt;
    }

    public Integer getTranOverheads() {
        return tranOverheads;
    }

    public void setTranOverheads(Integer tranOverheads) {
        this.tranOverheads = tranOverheads;
    }

    public Integer getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(Integer otherCharge) {
        this.otherCharge = otherCharge;
    }

    public Double getTotSaleAmt() {
        return totSaleAmt;
    }

    public void setTotSaleAmt(Double totSaleAmt) {
        this.totSaleAmt = totSaleAmt;
    }

    public List<SalesDet> getSalesDet() {
        return salesDet;
    }

    public void setSalesDet(List<SalesDet> salesDet) {
        this.salesDet = salesDet;
    }

    public static class SalesDet {

        @SerializedName("itmId")
        @Expose
        private Integer itmId;
        @SerializedName("unitPriceWiTax")
        @Expose
        private String unitPriceWiTax;
        @SerializedName("qty")
        @Expose
        private int qty;

        public Integer getItmId() {
            return itmId;
        }

        public void setItmId(Integer itmId) {
            this.itmId = itmId;
        }

        public String getUnitPriceWiTax() {
            return unitPriceWiTax;
        }

        public void setUnitPriceWiTax(String unitPriceWiTax) {
            this.unitPriceWiTax = unitPriceWiTax;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

    }

}
