package com.example.orderdeliver.data.repositories

import android.content.Context
import com.example.orderdeliver.data.config.REFRESH_AUTH_TOKEN_KEY
import com.example.orderdeliver.data.config.SHARED_PREF_NAME
import com.example.orderdeliver.data.config.SUCCESS_AUTH_TOKEN_KEY
import com.example.orderdeliver.domain.repositories.AuthTokenRepository
import com.example.orderdeliver.utils.showLog
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAuthTokenRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthTokenRepository {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override fun saveTokens(refreshToken: String, successToken: String) = with(sharedPrefs.edit()) {
        putString(REFRESH_AUTH_TOKEN_KEY, "Bearer $refreshToken")
        putString(SUCCESS_AUTH_TOKEN_KEY, "Bearer $successToken")
        apply()
    }

    override fun getRefreshToken(): String {
        return sharedPrefs.getString(REFRESH_AUTH_TOKEN_KEY, "") ?: ""
    }

    override fun getSuccessToken(): String {
        return sharedPrefs.getString(SUCCESS_AUTH_TOKEN_KEY, "") ?: ""
    }

}