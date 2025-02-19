package com.edfapg.sdk.views.edfacardpay

import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.model.request.options.EdfaPgSaleOptions
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.model.response.sale.EdfaPgSaleRedirect

data  class CardTransactionData(
    val order: EdfaPgSaleOrder,
    val payer: EdfaPgPayer,
    val card: EdfaPgCard,
    var response:EdfaPgSaleRedirect?
) : java.io.Serializable