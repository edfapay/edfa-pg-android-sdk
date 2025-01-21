package com.edfapg.sample.app

import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder

object SaleOrderValidator {
    fun validateOrder(order: EdfaPgSaleOrder): List<String> {
        val errors = mutableListOf<String>()

        if (order.id.isBlank()) {
            errors.add("id is empty")
        }

        if (order.amount <= 0) {
            errors.add("amount must be greater than 0")
        }

        if (order.currency.isBlank() || order.currency.length != 3) {
            errors.add("currency is invalid (must be a 3-character currency code)")
        }

        if (order.description.isBlank()) {
            errors.add("description is empty")
        }

        return errors
    }
}

