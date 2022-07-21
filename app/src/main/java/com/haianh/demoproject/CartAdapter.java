package com.haianh.demoproject;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haianh.demoproject.dao.CartDao;
import com.haianh.demoproject.dao.ProductDao;
import com.haianh.demoproject.model.Admin;
import com.haianh.demoproject.model.CartItemDetail;
import com.haianh.demoproject.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItemDetail> list;
    private Admin loginUser;
    private Context context;


    public CartAdapter(List<CartItemDetail> list, @NonNull Admin loginUser, Context context) {
        this.list = list;
        this.loginUser = loginUser;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItemDetail item = list.get(position);
        if (item == null) return;

        Product product = item.getProductInfo();
        holder.nameView.setText(product.getName());
        holder.priceView.setText(product.getPrice() + " đ");
        holder.quantityView.setText("Quantity: " + item.getItem().getQuantity());
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginUser == null) return;

                CartDao dao = new CartDao(v.getContext());
                dao.deleteCartItem(item.getItem().getId());

                ProductDao productDao = new ProductDao(v.getContext());
                productDao.updateProduct(new Product(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity() + item.getItem().getQuantity()
                ));

                if (context != null) {
                    ((Activity) context).recreate();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView priceView;
        private TextView quantityView;
        private Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.product_name);
            priceView = itemView.findViewById(R.id.product_price);
            quantityView = itemView.findViewById(R.id.product_quantity);
            removeButton = itemView.findViewById(R.id.button_remove);
        }
    }
}

