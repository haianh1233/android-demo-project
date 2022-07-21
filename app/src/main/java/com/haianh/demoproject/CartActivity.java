package com.haianh.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.haianh.demoproject.dao.CartDao;
import com.haianh.demoproject.dao.ProductDao;
import com.haianh.demoproject.model.Admin;
import com.haianh.demoproject.model.CartItem;
import com.haianh.demoproject.model.CartItemDetail;
import com.haianh.demoproject.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private Admin loginUser;
    private Button buyButton;
    private Button backButton;

    private void initBuy() {
        buyButton = findViewById(R.id.cart_view_buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartDao dao = new CartDao(CartActivity.this);
                if (loginUser == null) {
                    initUser();
                }
                dao.deleteAllCartItem(loginUser.getUsername());
                Toast.makeText(CartActivity.this, "Buy successful", Toast.LENGTH_SHORT).show();
                CartActivity.this.recreate();
            }
        });
    }

    private void initBack() {
        backButton = findViewById(R.id.cart_view_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginUser == null) {
                    initUser();
                }
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.putExtra("username", loginUser.getUsername());
                startActivity(intent);
            }
        });
    }


    private void initUser() {
        if (getIntent() == null) return;

        Intent intent = getIntent();
        String id = intent.getStringExtra("username");
        loginUser = new Admin(id,"");
    }

    private List<CartItemDetail> getCartItemDetails() {
        List<CartItemDetail> result = new ArrayList<>();

        CartDao cartDao = new CartDao(CartActivity.this);
        List<CartItem> listCartItems = cartDao.getCartItems(loginUser.getUsername());

        if (listCartItems != null && !listCartItems.isEmpty()) {
            ProductDao dao = new ProductDao(CartActivity.this);
            List<Product> listProducts = dao.getAllProduct();

            listCartItems.stream().forEach(cartItem -> {
                Product product = listProducts.stream()
                        .filter(item -> item.getId() == cartItem.getProductId())
                        .findAny().orElse(null);
                result.add(new CartItemDetail(cartItem, product));
            });
        }

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initBuy();
        initBack();
        initUser();

        List<CartItemDetail> list = getCartItemDetails();

        if (list == null || list.isEmpty()) {
            Intent intent = new Intent(CartActivity.this, ShopActivity.class);
            if (loginUser != null) {
                intent.putExtra("username", loginUser.getUsername());
            }
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;
        }

        RecyclerView cartRW = findViewById(R.id.cart_view_recycleView);
        CartAdapter adapter = new CartAdapter(list, loginUser, CartActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        cartRW.setLayoutManager(linearLayoutManager);
        cartRW.setAdapter(adapter);
    }
}