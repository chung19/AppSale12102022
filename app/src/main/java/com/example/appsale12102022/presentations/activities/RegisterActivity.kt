
package com.example.appsale12102022.presentations.activities
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale12102022.R
import com.example.appsale12102022.data.model.User
import com.example.appsale12102022.data.remote.AppResource
import com.example.appsale12102022.databinding.ActivityRegisterBinding
import com.example.appsale12102022.presentations.viewmodels.LoginViewModel
import com.example.appsale12102022.presentations.viewmodels.RegisterViewModel
import com.example.appsale12102022.utils.SpannedUtil
import com.example.appsale12102022.utils.ValidationUtil
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initial()
        observerData()
        setTextRegister()
        binding.registerButton.setOnClickListener {
            val email = binding.textEditEmail.text.toString()
            val password = binding.textEditPassword.text.toString()
            val name = binding.textEditName.text.toString()
            val phone = binding.textEditPhone.text.toString()
            val address = binding.textEditAddress.text.toString()
            if (!ValidationUtil.isValidEmail(email) ||
                !ValidationUtil.isValidPassword(password) ||
                !ValidationUtil.isPhoneNumber(phone) ||
                name.isEmpty() ||
                address.isEmpty()
            ) {
                Toast.makeText(this@RegisterActivity, "Invalid account or password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            registerViewModel.signUp(email, password, name, phone, address)
        }
    }
    private fun setTextRegister() {
        val spannableStringBuilder = SpannableStringBuilder()
        spannableStringBuilder.append("Already have an account?")
        spannableStringBuilder.append(
            SpannedUtil.setClickColorLink("Login", this) {
                finish()
            }
        )
        binding.textViewLogin.text = spannableStringBuilder
        binding.textViewLogin.highlightColor = Color.TRANSPARENT
        binding.textViewLogin.movementMethod = LinkMovementMethod.getInstance()
    }
    private fun observerData() {
        registerViewModel.userResourceLiveData.observe(this, Observer<AppResource<User>> { resource ->
            when (resource.status) {
                AppResource.Status.SUCCESS -> {
                    binding.layoutLoading.layoutLoading.visibility = View.GONE
                    Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.putExtra("email", binding.textEditEmail.text.toString())
                    intent.putExtra("password", binding.textEditPassword.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
                AppResource.Status.LOADING -> binding.layoutLoading.layoutLoading.visibility = View.VISIBLE
                AppResource.Status.ERROR -> {
                    binding.layoutLoading.layoutLoading.visibility = View.GONE
                    Toast.makeText(this@RegisterActivity, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun initial() {
        registerViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
           override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RegisterViewModel(this@RegisterActivity.application) as T
            }
        })[RegisterViewModel::class.java]
    }



}
