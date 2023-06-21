package com.example.nvgshop.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.example.nvgshop.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.EditText;
import androidx.annotation.NonNull;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class ChangePasswordDialogFragment extends AppCompatDialogFragment {
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private OnPasswordChangedListener passwordChangedListener;

    public interface OnPasswordChangedListener {
        void onPasswordChanged(String oldPassword, String newPassword);
    }

    public static ChangePasswordDialogFragment newInstance() {
        return new ChangePasswordDialogFragment();
    }

    public void setOnPasswordChangedListener(OnPasswordChangedListener listener) {
        this.passwordChangedListener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            passwordChangedListener = (OnPasswordChangedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPasswordChangedListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_change_password_dialog, null);

        oldPasswordEditText = view.findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);

        builder.setView(view)
                .setTitle("Đổi mật khẩu")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Đổi mật khẩu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPassword = oldPasswordEditText.getText().toString();
                        String newPassword = newPasswordEditText.getText().toString();
                        passwordChangedListener.onPasswordChanged(oldPassword, newPassword);
                    }
                });

        return builder.create();
    }
}

