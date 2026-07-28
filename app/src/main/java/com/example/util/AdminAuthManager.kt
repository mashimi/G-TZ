package com.example.util

import android.content.Context
import android.content.SharedPreferences

object AdminAuthManager {

    private const val PREFS_NAME = "deutsch_tz_admin_prefs"
    private const val KEY_ADMIN_PIN = "admin_pin_hash"
    private const val KEY_IS_ADMIN = "is_admin_authenticated"
    private const val KEY_SESSION_EXPIRY = "admin_session_expiry"

    // Default PIN for verification/dev
    private const val DEFAULT_PIN = "2580"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isAdminAuthenticated(context: Context): Boolean {
        val prefs = getPrefs(context)
        val expiry = prefs.getLong(KEY_SESSION_EXPIRY, 0L)
        return System.currentTimeMillis() < expiry
    }

    fun authenticate(context: Context, enteredPin: String): Boolean {
        val prefs = getPrefs(context)
        val storedPin = prefs.getString(KEY_ADMIN_PIN, null)

        val isValid = if (storedPin == null) {
            enteredPin == DEFAULT_PIN
        } else {
            enteredPin == storedPin
        }

        if (isValid) {
            val expiry = System.currentTimeMillis() + (30 * 60 * 1000L) // 30 minutes
            prefs.edit()
                .putBoolean(KEY_IS_ADMIN, true)
                .putLong(KEY_SESSION_EXPIRY, expiry)
                .apply()
        }
        return isValid
    }

    fun changePin(context: Context, newPin: String) {
        getPrefs(context).edit()
            .putString(KEY_ADMIN_PIN, newPin)
            .apply()
    }

    fun logout(context: Context) {
        getPrefs(context).edit()
            .putBoolean(KEY_IS_ADMIN, false)
            .putLong(KEY_SESSION_EXPIRY, 0L)
            .apply()
    }
}
