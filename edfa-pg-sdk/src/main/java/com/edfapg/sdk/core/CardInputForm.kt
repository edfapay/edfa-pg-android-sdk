package com.example.paymentgatewaynew.common

import Footer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.edfapg.sdk.R
import com.edfapg.sdk.core.EdfaPgSdk
import com.edfapg.sdk.core.handleSaleResponse
import com.edfapg.sdk.model.api.EdfaPgResult
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.sale.EdfaPgSaleCallback
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResult
import com.edfapg.sdk.toolbox.EdfaPgUtil
import com.edfapg.sdk.views.edfacardpay.CardTransactionData
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.edfapg.sdk.views.edfacardpay.EdfaCardPayFragment
import com.edfapg.sdk.views.edfacardpay.EdfaPgSaleWebRedirectActivity
import com.edfapg.sdk.views.edfacardpay.handleSaleResponse


@Composable
fun CardInputForm(
    cardHolderName: TextFieldValue,
    onCardHolderNameChange: (TextFieldValue) -> Unit,
    cardNumber: TextFieldValue,
    onCardNumberChange: (TextFieldValue) -> Unit,
    cvc: TextFieldValue,
    onCvcChange: (TextFieldValue) -> Unit,
    expiryDate: TextFieldValue,
    onExpiryDateChange: (TextFieldValue) -> Unit,
    xpressCardPay: EdfaCardPay?
) {
    // Hoist the state of these variables to preserve their values across recompositions
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var unformattedNumber by remember { mutableStateOf("") }

    var saleResponse: EdfaPgSaleResponse? = null
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .imePadding()
    ) {


        CardInputField(
            title = "Cardholder Name",
            placeholder = "Name",
            value = cardHolderName,
            inputType = KeyboardType.Text,
            action = ImeAction.Next,
            onValueChange = onCardHolderNameChange
        )

        CardInputField(
            title = "Card Number",
            placeholder = "**** **** **** ****",
            value = cardNumber,
            inputType = KeyboardType.Number,
            action = ImeAction.Next,

            onValueChange = { newValue ->
                if (newValue.text.length <= 19) {
                    val digitsOnly = newValue.text.replace(" ", "")
                    val formattedValue = digitsOnly.chunked(4).joinToString(" ")

                    // Calculate cursor position
                    val cursorOffset =
                        if (newValue.selection.start > 0 && (newValue.selection.start % 5 == 0)) 1 else 0
                    val cursorPosition = newValue.selection.start + cursorOffset
                    unformattedNumber = digitsOnly
                    // Update the card number with formatted text and new cursor position
                    onCardNumberChange(
                        newValue.copy(
                            text = formattedValue,
                            selection = TextRange(cursorPosition.coerceIn(0, formattedValue.length))
                        )
                    )
                }
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardInputField(
                modifier = Modifier.weight(1f),
                title = "CVV/CVC",
                placeholder = "000",
                inputType = KeyboardType.Number,
                action = ImeAction.Next,
                value = cvc,
                onValueChange = { newValue ->
                    if (newValue.text.length <= 3) {
                        // Preserve the current cursor position
                        val newText = newValue.text.replace(" ", "")
                        val selection = newValue.selection

                        // Update only the text, ensuring you keep the cursor position
                        onCvcChange(
                            TextFieldValue(
                                text = newText,
                                selection = TextRange(selection.start.coerceIn(0, newText.length))
                            )
                        )

                        // Store CVV value if necessary
                        cvv = newText
                    }
                },
            )

            Spacer(modifier = Modifier.width(16.dp))
            CardInputField(
                modifier = Modifier.weight(1f),
                title = "Exp. Date",
                placeholder = "MM/YY",
                inputType = KeyboardType.Number,
                action = ImeAction.Done,
                value = expiryDate,
                onValueChange = { newValue ->
                    // Remove any existing slashes and limit input to 4 digits (MMYY)
                    val digitsOnly = newValue.text.replace("/", "").take(4)

                    // Split the digits into month and year
                     month = digitsOnly.take(2)
                     year = digitsOnly.drop(2)

                    // Ensure the month is valid (01-12)
                    val validatedMonth = when {
                        month.isEmpty() -> ""
                        month.toIntOrNull() == null || month.toInt() > 12 -> "${month.first()}"
                        //                                month.first()>'1' -> "0$month"
                        else -> month
                    }

                    // Format as MM/YY
                    val formattedValue =
                        listOf(validatedMonth, year).filter { it.isNotEmpty() }
                            .joinToString("/")

                    val cursorShift = formattedValue.length - newValue.text.length


                    // Determine the cursor position
                    val originalCursorPosition = newValue.selection.start
                    val adjustedCursorPosition = when {
                        originalCursorPosition <= 2 -> originalCursorPosition
                        originalCursorPosition <= 4 -> originalCursorPosition + 1
                        else -> formattedValue.length
                    }

                    // Update the expiry date with formatted text and new cursor position
                    onExpiryDateChange(
                        newValue.copy(
                            text = formattedValue,
                            selection = TextRange(
                                adjustedCursorPosition.coerceIn(
                                    0,
                                    formattedValue.length
                                )
                            )
                        )
                    )
                },
            )

        }

        Spacer(modifier = Modifier.height(26.dp))
        Button(
            onClick = {
                val order = xpressCardPay?._order
                val payer = xpressCardPay?._payer
                if(order != null && payer != null){
                    if (month.isEmpty() || year.isEmpty()) {
                        println("Month or Year is empty")
                        return@Button
                    }
                    val card = EdfaPgCard(unformattedNumber, month.toInt(), year.toInt(), cvv)

                    saleResponse = null
                    EdfaPgSdk.Adapter.SALE.execute(
                        order = order,
                        card = card,
                        payer = payer,
                        termUrl3ds = EdfaPgUtil.ProcessCompleteCallbackUrl,
                        options = null,
                        auth = false,
                        callback = handleSaleResponse(CardTransactionData(order, payer, card, null))
                    )
                } else {
                    println("Something was empty")
                }
            },
            modifier = Modifier
                .height(50.dp)

                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.color_main)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Pay", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clickable {
//                    navController.popBackStack()
                }
        ) {
            Footer()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardInputField(
    modifier: Modifier = Modifier,
    title: String = "Cardholder Name",
    placeholder: String = "Name",
    value: TextFieldValue,
    inputType: KeyboardType,
    action: ImeAction = ImeAction.Next,
    onValueChange: (TextFieldValue) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = 10.dp)
            .background(
                Color(0xFFF1F4F8),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 5.dp)
                    .zIndex(3f)
                    .background(
                        Color(Color.White.value.toLong()),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = title,
                    color = Color(0xFF8F9BB3),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF1F4F8)) // Background color
                        .padding(horizontal = 8.dp) // Padding inside the text field
                        .focusable(true),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = inputType,
                        imeAction = action
                    ),
                    cursorBrush = SolidColor(Color(0xFF2C3246)), // Cursor color
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
//                            if (value.text.isEmpty()) {
//                                Text(
//                                    text = placeholder,
//                                    fontSize = 15.sp,
//                                    color = Color.Black, // Placeholder color
//                                )
//                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }

}