package com.example.appsale12102022.utils;

import java.text.DecimalFormat;

/**
 * Created by pphat on 2/6/2023.
 */
public class StringUtil {
    public static String formatCurrency(int number) {
        return new DecimalFormat("#,###").format(number);
    }
}
