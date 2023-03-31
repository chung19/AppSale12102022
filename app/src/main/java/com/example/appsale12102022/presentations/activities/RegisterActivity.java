package com.example.appsale12102022.presentations.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Toast;

import com.example.appsale12102022.R;
import com.example.appsale12102022.data.model.User;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.databinding.ActivityRegisterBinding;
import com.example.appsale12102022.presentations.viewmodels.LoginViewModel;
import com.example.appsale12102022.presentations.viewmodels.RegisterViewModel;
import com.example.appsale12102022.utils.SpannedUtil;
import com.example.appsale12102022.utils.ValidationUtil;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initial();
        observerData();
        setTextRegister();

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.textEditEmail.getText().toString();
                String password = binding.textEditPassword.getText().toString();
                String name = binding.textEditName.getText().toString();
                String phone = binding.textEditPhone.getText().toString();
                String address = binding.textEditAddress.getText().toString();

                if (!ValidationUtil.isValidEmail(email) ||
                    !ValidationUtil.isValidPassword(password) ||
                    !ValidationUtil.isPhoneNumber(phone) ||
                    name.isEmpty() ||
                    address.isEmpty()
                ) {
                    Toast.makeText(RegisterActivity.this, "Invalid account or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerViewModel.signUp(email, password, name, phone, address);
            }
        });
    }

    private void setTextRegister() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("Already have an account?");
        spannableStringBuilder.append(SpannedUtil.setClickColorLink("Login", this, new SpannedUtil.OnListenClick() {
            @Override
            public void onClick() {
                finish();
            }
        }));
        binding.textViewLogin.setText(spannableStringBuilder);
        binding.textViewLogin.setHighlightColor(Color.TRANSPARENT);
        binding.textViewLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void observerData() {
        registerViewModel.getUserResource().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("email", binding.textEditEmail.getText().toString());
                        intent.putExtra("password", binding.textEditPassword.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case LOADING:
                        binding.layoutLoading.layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void initial() {
        registerViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RegisterViewModel(RegisterActivity.this);
            }
        }).get(RegisterViewModel.class);
    }
}
