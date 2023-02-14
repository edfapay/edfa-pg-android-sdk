/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.core

import android.content.Context
import androidx.core.content.edit

/**
 * The [ExpresspayCredential] values storage.
 *
 * @param context the app context.
 */
internal class ExpresspayStorage(context: Context) {

    companion object {
        private const val EXPRESSPAY_STORAGE = "EXPRESSPAY_STORAGE"
    }

    private val storage = context.getSharedPreferences(EXPRESSPAY_STORAGE, Context.MODE_PRIVATE)

    fun setCredential(key: String, value: String) = storage.edit {
        putString(key, value)
    }

    fun getCredentials(key: String) = storage.getString(key, null).orEmpty()
}
