package com.lazydevs.tinylensadmin.Model;

public class ModelEarn {

    String deliveryDate;
    String profitBalance;

    public ModelEarn(String deliveryDate, String profitBalance) {
        this.deliveryDate = deliveryDate;
        this.profitBalance = profitBalance;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getProfitBalance() {
        return profitBalance;
    }

    public void setProfitBalance(String profitBalance) {
        this.profitBalance = profitBalance;
    }
}
