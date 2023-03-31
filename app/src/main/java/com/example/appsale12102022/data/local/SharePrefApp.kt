package com.example.appsale12102022.data.local
import android.content.Context
import android.content.SharedPreferences
class SharePrefApp private constructor(context: Context) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("share_pref_app", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor? = null

    companion object {
        @Volatile
        private var instance: SharePrefApp? = null

        fun getInstance(context: Context): SharePrefApp {
            return instance ?: synchronized(this) {
                instance ?: SharePrefApp(context).also { instance = it }
            }
        }
    }

    fun saveDataString(key: String, value: String) {
        editor = sharedPreferences.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getDataString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun clearCache() {
        editor = sharedPreferences.edit()
        editor?.clear()
        editor?.apply()
    }
}
