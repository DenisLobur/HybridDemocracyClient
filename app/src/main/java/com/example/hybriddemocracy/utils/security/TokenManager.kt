package com.example.hybriddemocracy.utils.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

class TokenManager @Inject constructor(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // To save the token
    fun saveToken(token: String) {
        sharedPreferences.edit().putString("jwt_token", token).apply()
    }


    // To retrieve the token
    fun getToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }

    // To clear the token
    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove("jwt_token")
        editor.apply()
    }

}

