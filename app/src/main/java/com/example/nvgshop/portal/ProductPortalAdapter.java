package com.example.nvgshop.portal;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvgshop.R;
import com.example.nvgshop.models.Product;

import java.util.List;

public class ProductPortalAdapter extends RecyclerView.Adapter<ProductPortalAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductPortalAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName;
        private TextView textViewProductPrice;
        private TextView textViewProductDescription;
        private Button buttonAddToCart;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewProductDescription = itemView.findViewById(R.id.textViewProductDescription);
            buttonAddToCart = itemView.findViewById(R.id.buttonAddToCart);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_portal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.textViewProductName.setText(product.getName());
        holder.textViewProductPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewProductDescription.setText(product.getDescription());

        int imageResId = getImageResIdByProductType(product.getType());
        holder.imageViewProduct.setImageResource(imageResId);

        holder.buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private int getImageResIdByProductType(String productType) {
        switch (productType) {
            case "Shirt":
                return R.drawable.ao;
            case "Toy":
                return R.drawable.toy;
            case "Book":
                return R.drawable.images;
            default:
                return R.drawable.hoaqua;
        }
    }

    private void addToCart(Product product) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        SharedPreferences cartPreferences = context.getSharedPreferences("MyCart", Context.MODE_PRIVATE);
        SharedPreferences.Editor cartEditor = cartPreferences.edit();

        String orderDetail = "Product ID: " + product.getId() + ", Product Name: " + product.getName() + ", User: " + username;

        cartEditor.putString("order_detail", orderDetail);
        cartEditor.apply();

        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
    }
}


