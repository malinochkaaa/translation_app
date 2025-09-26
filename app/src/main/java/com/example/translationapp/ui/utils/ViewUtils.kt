package com.example.translationapp.ui.utils

import android.content.Context
import android.widget.Toast

object ViewUtils {
    fun showErrorMessage(
        context: Context,
        message: Int,
    ) = Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()
}