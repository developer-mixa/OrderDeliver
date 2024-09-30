package com.example.orderdeliver.presentation.plugins.plugins

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToastPlugin @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun toast(messageRes: Int) =
        Toast.makeText(context, messageRes, Toast.LENGTH_LONG).show()

    fun toast(messageString: String) =
        Toast.makeText(context, messageString, Toast.LENGTH_LONG).show()

}