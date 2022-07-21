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
import com.haianh.demoproject.model.CartItem;
import com.haianh.demoproject.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Admin loginUser;
    private Context context;

    public ProductAdapter(List<Product> productList, Admin loginUser, Context context) {
        this.productList = productList;
        this.loginUser = loginUser;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product == null) return;

        holder.nameView.setText(product.getName());
        holder.priceView.setText(product.getPrice() + " đ");
        holder.quantityView.setText("Quantity: " + product.getQuantity());
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginUser == null) return;

                CartDao dao = new CartDao(v.getContext());
                CartItem item = dao.getCartItem(loginUser.getUsername(), product.getId());
                if (item != null) {
                    dao.updateCart(item.getId(), item.getQuantity() + 1);
                } else {
                    dao.insertCart(loginUser.getUsername(), product.getId(), 1);
                }

                ProductDao productDao = new ProductDao(v.getContext());
                productDao.updateProduct(new Product(product.getId(), product.getName(), product.getPrice(), product.getQuantity() - 1));
                if (context != null) {
                    ((Activity) context).recreate();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView priceView;
        private TextView quantityView;
        private Button addButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.product_name);
            priceView = itemView.findViewById(R.id.product_price);
            quantityView = itemView.findViewById(R.id.product_quantity);
            addButton = itemView.findViewById(R.id.button_add);
        }
    }
}
