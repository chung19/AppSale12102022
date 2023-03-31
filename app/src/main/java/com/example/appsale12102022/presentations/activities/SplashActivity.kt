package com.example.appsale12102022.presentations.activities
import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appsale12102022.R
import com.example.appsale12102022.common.AppConstant
import com.example.appsale12102022.data.local.SharePrefApp
import com.example.appsale12102022.databinding.ActivitySplashBinding
class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        splashBinding.splashView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                val sharePrefApp = SharePrefApp.getInstance(this@SplashActivity)
                val token = sharePrefApp.getDataString(AppConstant.KEY_TOKEN)
                val intent = if (token == null) {
                    Intent(this@SplashActivity, LoginActivity::class.java)
                } else {
                    Intent(this@SplashActivity, ProductActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }
}