package com.example.appsale12102022.presentations.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.example.appsale12102022.R;
import com.example.appsale12102022.common.AppConstant;
import com.example.appsale12102022.data.local.SharePrefApp;
import com.example.appsale12102022.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding splashBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());

        splashBinding.splashView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                SharePrefApp sharePrefApp = SharePrefApp.getInstance(SplashActivity.this);
                String token = sharePrefApp.getDataString(AppConstant.KEY_TOKEN);
                Intent intent;
                if (token == null) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, ProductActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
