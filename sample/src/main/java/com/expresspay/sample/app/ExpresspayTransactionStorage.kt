/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sample.app

import android.content.Context
import androidx.core.content.edit
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.api.ExpresspayStatus
import com.expresspay.sdk.model.response.base.result.IExpresspayResult
import com.google.gson.Gson
import java.util.*

internal class ExpresspayTransactionStorage(context: Context) {

    companion object {
        private const val EXPRESSPAY_TRANSACTION_STORAGE = "EXPRESSPAY_TRANSACTION_STORAGE"
    }

    private val storage = context.getSharedPreferences(EXPRESSPAY_TRANSACTION_STORAGE, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addTransaction(transaction: Transaction) {
        storage.edit {
            putString(
                UUID.randomUUID().toString(),
                gson.toJson(transaction)
            )
        }
    }

    fun getAllTransactions() = storage.all.map {
        gson.fromJson(it.value as String, Transaction::class.java)
    }

    fun getRecurringSaleTransactions() = getAllTransactions().filter {
        it.action == ExpresspayAction.SALE && it.recurringToken.isNotEmpty()
    }

    fun getCaptureTransactions() = getAllTransactions().filter {
        it.action == ExpresspayAction.SALE && it.isAuth
    }

    fun getCreditvoidTransactions() = getAllTransactions().filter {
        it.action == ExpresspayAction.SALE || it.action == ExpresspayAction.CAPTURE || it.isAuth
    }

    fun clearTransactions() = storage.edit {
        clear()
    }

    data class Transaction(
        val payerEmail: String,
        val cardNumber: String,
    ) {
        var id: String = ""
        var action: ExpresspayAction? = null
        var result: ExpresspayResult? = null
        var status: ExpresspayStatus? = null

        var recurringToken: String = ""
        var isAuth: Boolean = false

        fun fill(result: IExpresspayResult) {
            id = result.transactionId
            action = result.action
            this.result = result.result
            status = result.status
        }
    }
}
