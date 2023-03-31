package com.example.appsale12102022.utils

import android.text.TextUtils
import android.util.Patterns

/**
 * Created by pphat on 2/3/2023.
 */
object ValidationUtil {
    fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return !password.isEmpty() && password.length > 7
    }

    fun isPhoneNumber(phone: String): Boolean {
        return !phone.isEmpty() && phone.length > 9
    }
}