package com.app.vxresumebuilder.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object Utilities {
    fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
}
