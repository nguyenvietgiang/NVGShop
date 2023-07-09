package com.example.nvgshop.portal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvgshop.R;
import com.example.nvgshop.models.Product;

import java.util.List;

public class ProductPortalAdapter extends RecyclerView.Adapter<ProductPortalAdapter.ViewHolder> {
    private List<Product> productList;

    // Constructor
    public ProductPortalAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName;
        private TextView textViewProductPrice;
        private TextView textViewProductDescription;
        private Button buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewProductDescription = itemView.findViewById(R.id.textViewProductDescription);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
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


        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}

