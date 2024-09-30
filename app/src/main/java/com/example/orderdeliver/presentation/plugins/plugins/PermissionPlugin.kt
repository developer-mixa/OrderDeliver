package com.example.orderdeliver.presentation.plugins.plugins

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionPlugin @Inject constructor(
) : ActivityPlugin() {

    fun requestPermissions(vararg permissions: String) = whenActivityActive {

        val allPermissionsNotGranted = permissions.any { permission ->
            ActivityCompat.checkSelfPermission(it, permission) != PackageManager.PERMISSION_GRANTED
        }

        if (!allPermissionsNotGranted) return@whenActivityActive

        ActivityCompat.requestPermissions(it, permissions, 0)
    }

}