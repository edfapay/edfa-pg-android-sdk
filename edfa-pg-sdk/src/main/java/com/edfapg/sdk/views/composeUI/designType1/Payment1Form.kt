package com.example.paymentgatewaynew.payment1

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.example.paymentgatewaynew.common.CardInputForm

@Composable
fun Payment1Form(xpressCardPay: EdfaCardPay?,activity: Activity,sale3dsRedirectLauncher: ActivityResultLauncher<Intent>) {
    var cardHolderName by remember {
        mutableStateOf(TextFieldValue("", selection = TextRange(0)))
    }
    var cardNumber by remember {
        mutableStateOf(TextFieldValue("", selection = TextRange(0)))
    }
    var expiryDate by remember { mutableStateOf(TextFieldValue("", selection = TextRange(0))) }
    var cvc by remember {  mutableStateOf(TextFieldValue("", selection = TextRange(0))) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        CardForm(cardNumber, cardHolderName, expiryDate, cvc)

        CardInputForm(
            cardHolderName = cardHolderName,
            onCardHolderNameChange = { cardHolderName = it },
            cardNumber = cardNumber,
            onCardNumberChange = { cardNumber = it },
            cvc = cvc,
            onCvcChange = { cvc = it },
            expiryDate = expiryDate,
            onExpiryDateChange = { expiryDate = it },
            xpressCardPay = xpressCardPay,
            activity = activity,
            sale3dsRedirectLauncher = sale3dsRedirectLauncher
        )
    }
}
