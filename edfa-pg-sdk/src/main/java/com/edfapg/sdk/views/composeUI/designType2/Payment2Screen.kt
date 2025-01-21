package com.example.paymentgatewaynew.payment2

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edfapg.sdk.R
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.example.paymentgatewaynew.common.CardInputForm
import com.example.paymentgatewaynew.payment1.Payment1Form


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Payment2Screen(navController: NavController,xpressCardPay: EdfaCardPay?,activity: Activity,sale3dsRedirectLauncher: ActivityResultLauncher<Intent>) {
    var bottomSheetVisible by remember { mutableStateOf(true) }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newState ->
            newState != SheetValue.Expanded
        })

    ModalBottomSheet(
        sheetState = bottomSheetState,
        modifier = Modifier.fillMaxHeight(0.9f),
        containerColor = Color.White,
        onDismissRequest = { bottomSheetVisible = false }
    ) {
        CardEntryForm(navController,xpressCardPay,activity,sale3dsRedirectLauncher)
    }
}
@Composable
fun TitleAmount(amount:String,currency:String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(214.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.txt_total),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(5.dp),
            fontSize = 16.sp
        )
        Text(
            text = "$amount $currency",
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(5.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    }
}

@Composable
fun CardEntryForm(navController:NavController,xpressCardPay: EdfaCardPay?,activity: Activity,sale3dsRedirectLauncher: ActivityResultLauncher<Intent>) {
    var cardNumber by remember { mutableStateOf(TextFieldValue("")) }
    var cardHolderName by remember { mutableStateOf(TextFieldValue("")) }
    var expiryDate by remember { mutableStateOf(TextFieldValue("")) }
    var cvc by remember { mutableStateOf(TextFieldValue("")) }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Spacer(modifier = Modifier.width(16.dp))
            Box{
                xpressCardPay?._order?.let {
                    val amount = it.formattedAmount() // Get formatted amount from order
                    val currency = it.formattedCurrency() // Get formatted currency from order
                    TitleAmount(amount, currency)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Card2Form(
                cardNumber = cardNumber,
                cardHolderName = cardHolderName,
                expiryDate = expiryDate,
                cvc = cvc
            )
        }
        Spacer(modifier = Modifier.height(26.dp))
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
