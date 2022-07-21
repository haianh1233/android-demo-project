package com.haianh.demoproject.model;


import androidx.annotation.NonNull;

public class CartItemDetail {
    private CartItem item;
    private Product productInfo;
    private Double totalPrice;

    public CartItemDetail(@NonNull CartItem item, @NonNull Product productInfo) {
        this.item = item;
        this.productInfo = productInfo;
        this.totalPrice = item.getQuantity() * productInfo.getPrice();
    }

    public CartItem getItem() {
        return item;
    }

    public void setItem(CartItem item) {
        this.item = item;
    }

    public Product getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(Product productInfo) {
        this.productInfo = productInfo;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartItemDetail{" +
                "item=" + item +
                ", productInfo=" + productInfo +
                ", totalPrice=" + totalPrice + "đ" +
                '}';
    }
}
