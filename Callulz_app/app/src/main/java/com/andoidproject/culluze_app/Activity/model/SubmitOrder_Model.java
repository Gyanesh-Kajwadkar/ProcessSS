package com.andoidproject.culluze_app.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmitOrder_Model {
    @SerializedName("usrId")
    @Expose
    private Integer usrId;
    @SerializedName("custmrId")
    @Expose
    private Integer custmrId;
    @SerializedName("totSaleAmt")
    @Expose
    private Float totSaleAmt;
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

    public Float getTotSaleAmt() {
        return totSaleAmt;
    }

    public void setTotSaleAmt(Float totSaleAmt) {
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
