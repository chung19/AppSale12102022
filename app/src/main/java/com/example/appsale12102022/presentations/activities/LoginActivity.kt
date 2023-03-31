
package com.example.appsale12102022.presentations.activities
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale12102022.data.model.User
import com.example.appsale12102022.data.remote.AppResource
import com.example.appsale12102022.databinding.ActivityLoginBinding
import com.example.appsale12102022.presentations.viewmodels.LoginViewModel
import com.example.appsale12102022.utils.SpannedUtil
import com.example.appsale12102022.utils.ValidationUtil
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private val REQUEST_CODE_REGISTER = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
        observerData()
        setTextRegister()
        binding.signIn.setOnClickListener {
            val email = binding.textEditEmail.text.toString()
            val password = binding.textEditPassword.text.toString()
            if (!ValidationUtil.isValidEmail(email) || !ValidationUtil.isValidPassword(password)) {
                Toast.makeText(this@LoginActivity, "Invalid account or password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginViewModel.signIn(email, password)
        }
    }
    private fun initial() {
        loginViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @NonNull
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return LoginViewModel(application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }).get(LoginViewModel::class.java)
    }

    private fun observerData() {
        loginViewModel.userResource.observe(this, Observer<AppResource<User>> { resource ->
            when (resource.status) {
                AppResource.Status.SUCCESS -> {
                    binding.layoutLoading.layoutLoading.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, ProductActivity::class.java))
                    finish()
                }
                AppResource.Status.LOADING -> {
                    binding.layoutLoading.layoutLoading.visibility = View.VISIBLE
                }
                AppResource.Status.ERROR -> {
                    binding.layoutLoading.layoutLoading.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun setTextRegister() {
        val spannableStringBuilder = SpannableStringBuilder()
        spannableStringBuilder.append("Don't have an account?")
        spannableStringBuilder.append(SpannedUtil.setClickColorLink("Register", this) {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_REGISTER)
        })
        binding.textViewRegister.text = spannableStringBuilder
        binding.textViewRegister.highlightColor = Color.TRANSPARENT
        binding.textViewRegister.movementMethod = LinkMovementMethod.getInstance()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK && data != null) {
            val email = data.getStringExtra("email")
            val password = data.getStringExtra("password")
            binding.textEditEmail.setText(email)
            binding.textEditPassword.setText(password)
        }
    }
}
