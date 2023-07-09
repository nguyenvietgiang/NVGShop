package com.example.nvgshop.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvgshop.Fragment.EditProductFragment;
import com.example.nvgshop.R;
import com.example.nvgshop.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public List<Product> productList;
    private OnDeleteClickListener onDeleteClickListener;
    private OnEditClickListener onEditClickListener;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.onEditClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewProductName.setText(product.getName());
        holder.textViewProductPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewProductDescription.setText(product.getDescription());
        holder.textViewProductType.setText(product.getType());

        // Hiển thị ảnh tương ứng với loại sản phẩm
        int imageResId = getImageResIdByProductType(product.getType());
        holder.imageViewProduct.setImageResource(imageResId);

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditClickListener != null) {
                    onEditClickListener.onEditClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewProductName;
        public TextView textViewProductPrice;
        public TextView textViewProductDescription;
        public TextView textViewProductType;
        public Button buttonDelete;
        public Button buttonEdit;
        public ImageView imageViewProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewProductDescription = itemView.findViewById(R.id.textViewProductDescription);
            textViewProductType = itemView.findViewById(R.id.textViewProductType);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
        }
    }

    public void setData(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
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

