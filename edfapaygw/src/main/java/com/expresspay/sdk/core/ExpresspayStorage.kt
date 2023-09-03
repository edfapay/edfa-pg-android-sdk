/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapaygw.sdk.core

import android.content.Context
import androidx.core.content.edit

/**
 * The [EdfaPgCredential] values storage.
 *
 * @param context the app context.
 */
internal class EdfaPgStorage(context: Context) {

    companion object {
        private const val EDFA_PG_STORAGE = "EDFA_PG_STORAGE"
    }

    private val storage = context.getSharedPreferences(EDFA_PG_STORAGE, Context.MODE_PRIVATE)

    fun setCredential(key: String, value: String) = storage.edit {
        putString(key, value)
    }

    fun getCredentials(key: String) = storage.getString(key, null).orEmpty()
}
