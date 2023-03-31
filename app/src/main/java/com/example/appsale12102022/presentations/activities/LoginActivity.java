package com.example.appsale12102022.presentations.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appsale12102022.R;
import com.example.appsale12102022.data.model.User;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.data.remote.dto.UserDTO;
import com.example.appsale12102022.databinding.ActivityLoginBinding;
import com.example.appsale12102022.presentations.viewmodels.LoginViewModel;
import com.example.appsale12102022.utils.SpannedUtil;
import com.example.appsale12102022.utils.ValidationUtil;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;
    int REQUEST_CODE_REGISTER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initial();
        observerData();

        setTextRegister();

        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.textEditEmail.getText().toString();
                String password = binding.textEditPassword.getText().toString();

                if (!ValidationUtil.isValidEmail(email) || !ValidationUtil.isValidPassword(password)) {
                    Toast.makeText(LoginActivity.this, "Invalid account or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginViewModel.signIn(email, password);
            }
        });
    }

    private void initial() {
        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LoginViewModel(LoginActivity.this);
            }
        }).get(LoginViewModel.class);
    }

    private void observerData() {
        loginViewModel.getUserResource().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, ProductActivity.class));
                        finish();
                        break;
                    case LOADING:
                        binding.layoutLoading.layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setTextRegister() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append("Don't have an account?");
        spannableStringBuilder.append(SpannedUtil.setClickColorLink("Register", this, new SpannedUtil.OnListenClick() {
            @Override
            public void onClick() {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE_REGISTER);
            }
        }));
        binding.textViewRegister.setText(spannableStringBuilder);
        binding.textViewRegister.setHighlightColor(Color.TRANSPARENT);
        binding.textViewRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK && data != null) {
            String email = data.getStringExtra("email");
            String password = data.getStringExtra("password");

            binding.textEditEmail.setText(email);
            binding.textEditPassword.setText(password);
        }
    }
}
