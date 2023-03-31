package com.example.appsale12102022.utils

import java.text.DecimalFormat

/**
 * Created by pphat on 2/6/2023.
 */
object StringUtil {
    fun formatCurrency(number: Int): String {
        return DecimalFormat("#,###").format(number.toLong())
    }
}