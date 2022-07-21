package com.haianh.demoproject.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private String username;
    private Map<Integer, Integer> items;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void addToCart(int id) {
        addToCart(id, 1);
    }

    private void addToCart(int id, int quantity) {
        if (items == null) {
            items = new HashMap<>();
        }

        int newQuantity = quantity;
        if (items.containsKey(id)) {
            newQuantity = items.get(id) + 1;
        }

        items.put(id, newQuantity);
    }

    public void removeItem(String id) {
        if (items == null) {
            return;
        }

        if (items.containsKey(id)) {
            items.remove(id);
        }

        if (items.isEmpty()) {
            items = null;
        }
    }

    public void addItems(List<CartItem> list) {
        list.forEach((CartItem cartItem) -> {
            int id = cartItem.getProductId();
            int quantity = cartItem.getQuantity();
            addToCart(id, quantity);
        });
    }

    public int getTotalQuantity() {
        if (items == null) {
            return 0;
        }

        int quantity = 0;
        for (int id : items.keySet()) {
            quantity += items.get(id);
        }

        return quantity;
    }

    public boolean isEmpty() {
        return items == null;
    }
}
