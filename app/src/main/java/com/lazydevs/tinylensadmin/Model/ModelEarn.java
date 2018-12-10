package com.lazydevs.tinylensadmin.Model;

public class ModelEarn {

    String deliveryDate;
    String profitBalance;
    String userId;

    public ModelEarn(String deliveryDate, String profitBalance, String userId) {
        this.deliveryDate = deliveryDate;
        this.profitBalance = profitBalance;
        this.userId=userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
