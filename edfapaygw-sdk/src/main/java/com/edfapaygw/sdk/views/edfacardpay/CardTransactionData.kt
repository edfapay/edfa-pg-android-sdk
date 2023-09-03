package com.edfapaygw.sdk.views.edfacardpay

import com.edfapaygw.sdk.model.request.card.EdfaPgCard
import com.edfapaygw.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapaygw.sdk.model.request.payer.EdfaPgPayer
import com.edfapaygw.sdk.model.response.sale.EdfaPgSaleRedirect

data  class CardTransactionData(
    val order: EdfaPgSaleOrder,
    val payer: EdfaPgPayer,
    val card: EdfaPgCard,
    var response:EdfaPgSaleRedirect?
) : java.io.Serializable