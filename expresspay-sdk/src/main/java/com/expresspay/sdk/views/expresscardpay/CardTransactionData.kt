package com.expresspay.sdk.views.expresscardpay

import androidx.annotation.NonNull
import com.expresspay.sdk.model.request.card.ExpresspayCard
import com.expresspay.sdk.model.request.order.ExpresspaySaleOrder
import com.expresspay.sdk.model.request.payer.ExpresspayPayer
import com.expresspay.sdk.model.response.sale.ExpresspaySaleRedirect

data  class CardTransactionData(
    val order: ExpresspaySaleOrder,
    val payer: ExpresspayPayer,
    val card: ExpresspayCard,
    var response:ExpresspaySaleRedirect?
) : java.io.Serializable