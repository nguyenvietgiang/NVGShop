package com.example.nvgshop.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nvgshop.Data.DatabaseHelper;
import com.example.nvgshop.R;
import com.example.nvgshop.admin.ProductAdapter;

public class EditProductFragment extends DialogFragment {

    private static final String ARG_PRODUCT_ID = "arg_product_id";
    private static final String ARG_PRODUCT_NAME = "arg_product_name";
    private static final String ARG_PRODUCT_DESCRIPTION = "arg_product_description";
    private static final String ARG_PRODUCT_PRICE = "arg_product_price";

    private int productId;
    private String productName;
    private String productDescription;
    private ProductAdapter productAdapter;
    private EditText editTextProductName;
    private EditText editTextProductDescription;

    public EditProductFragment() {

    }

    public static EditProductFragment newInstance(int productId, String productName, String productDescription, double productPrice) {
        EditProductFragment fragment = new EditProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT_ID, productId);
        args.putString(ARG_PRODUCT_NAME, productName);
        args.putString(ARG_PRODUCT_DESCRIPTION, productDescription);
        args.putDouble(ARG_PRODUCT_PRICE, productPrice);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            productId = getArguments().getInt(ARG_PRODUCT_ID);
            productName = getArguments().getString(ARG_PRODUCT_NAME);
            productDescription = getArguments().getString(ARG_PRODUCT_DESCRIPTION);
        }

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_product, null);

        editTextProductName = view.findViewById(R.id.editTextProductName);
        editTextProductDescription = view.findViewById(R.id.editTextProductDescription);

        editTextProductName.setText(productName);
        editTextProductDescription.setText(productDescription);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Chỉnh sửa sản phẩm")
                .setView(view)
                .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newProductName = editTextProductName.getText().toString();
                        String newProductDescription = editTextProductDescription.getText().toString();

                        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                        double productPrice = 0.0;
                        String type = "Type2";
                        databaseHelper.updateProduct(productId, newProductName, newProductDescription, productPrice,type);
                        if (productAdapter != null) {
                            productAdapter.notifyDataSetChanged();
                        }
                        if (getTargetFragment() instanceof EditProductListener) {
                            EditProductListener listener = (EditProductListener) getTargetFragment();
                            listener.onProductEdited(productId, newProductName, newProductDescription);
                        }
                    }
                })
                .setNegativeButton("Hủy", null);

        return builder.create();
    }
    public interface EditProductListener {
        void onProductEdited(int productId, String productName, String productDescription);
    }
}






