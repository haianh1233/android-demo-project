package com.haianh.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.haianh.demoproject.dao.ProductDao;
import com.haianh.demoproject.model.Admin;
import com.haianh.demoproject.model.Product;

import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private Admin loginUser;

    private List<Product> getProducts() {
        ProductDao dao = new ProductDao(ShopActivity.this);
        return dao.getAllProduct();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent = getIntent();
        String name = intent.getStringExtra("username");
        loginUser = new Admin(name, "");

        List<Product> productList = getProducts();
        RecyclerView productRW = findViewById(R.id.productRecycleView);
        ProductAdapter productAdapter = new ProductAdapter(productList, loginUser, ShopActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShopActivity.this);
        productRW.setLayoutManager(linearLayoutManager);
        productRW.setAdapter(productAdapter);
    }
}