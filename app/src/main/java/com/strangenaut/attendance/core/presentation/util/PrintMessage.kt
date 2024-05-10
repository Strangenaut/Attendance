package com.strangenaut.attendance.core.presentation.util

import android.content.Context
import android.widget.Toast

fun printMessage(context: Context, message: String) {
    if (message.isNotEmpty()) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}