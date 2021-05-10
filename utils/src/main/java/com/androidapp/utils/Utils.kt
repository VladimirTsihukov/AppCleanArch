package com.androidapp.utils

import android.app.AlertDialog
import android.content.Context

fun getAlertDialog(context: Context): AlertDialog {
    return AlertDialog.Builder(context)
        .setMessage(R.string.dialog_message_device_is_offline)
        .setTitle(R.string.dialog_title_device_is_offline)
        .setCancelable(true)
        .setPositiveButton(R.string.dialog_button_cancel) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
}