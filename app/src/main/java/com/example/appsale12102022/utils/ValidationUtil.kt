package com.example.appsale12102022.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by pphat on 2/3/2023.
 */
public class ValidationUtil {

    public static boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isValidPassword(String password) {
        return !password.isEmpty() && password.length() > 7;
    }

    public static boolean isPhoneNumber(String phone) {
        return !phone.isEmpty() && phone.length() > 9;
    }
}
