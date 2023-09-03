package com.edfapaygw.sdk.toolbox

import android.content.Context
import android.webkit.SslErrorHandler
import androidx.appcompat.app.AlertDialog
import com.edfapaygw.sdk.R

fun SslErrorHandler.userConfirmation(context: Context, ok:()->Unit) {
    val alert = AlertDialog.Builder(context)
    alert.setMessage(R.string.notification_error_ssl_cert_invalid)
    alert.setPositiveButton(R.string.cancel) { dialog, which -> ok() }

//    alert.setNegativeButton(R.string.no) { dialog, which -> no() }
}