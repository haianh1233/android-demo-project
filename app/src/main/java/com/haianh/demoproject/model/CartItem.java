package com.haianh.demoproject.model;

public class CartItem {
    private int id;
    private String accountId;
    private int productId;
    private int quantity;

    public CartItem(int id, String accountId, int productId, int quantity) {
        this.id = id;
        this.accountId = accountId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
