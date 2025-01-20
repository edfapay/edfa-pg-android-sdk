package com.example.paymentgatewaynew.common

import Footer
import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.Size
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.edfapg.sdk.PaymentActivity
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
import com.edfapg.sdk.toolbox.EdfaPgValidation.Card
import com.edfapg.sdk.views.edfacardpay.CardTransactionData
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.edfapg.sdk.views.edfacardpay.EdfaCardPayFragment
import com.edfapg.sdk.views.edfacardpay.EdfaPgSaleWebRedirectActivity
import com.edfapg.sdk.views.edfacardpay.handleSaleResponse
import org.junit.runner.manipulation.Ordering.Context
import java.util.Calendar


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
    xpressCardPay: EdfaCardPay?,
    activity: Activity?,
    sale3dsRedirectLauncher: ActivityResultLauncher<Intent>
) {
    // Hoist the state of these variables to preserve their values across recompositions
    var month by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var unformattedNumber by remember { mutableStateOf("") }

    var isButtonClicked by remember { mutableStateOf(false) }
    var isResponseReceived by remember { mutableStateOf(false) }

    // Track validity of each field
    var isCardNumberValid by remember { mutableStateOf(false) }
    var isCvvValid = cvv.length in Card.CVV_MIN.toInt()..Card.CVV_MAX.toInt()
    var isMonthValid = month.toIntOrNull()?.let {
        it in Card.MONTH_MIN.toInt()..Card.MONTH_MAX.toInt()
    } ?: false
    var isYearValid = false

    var isFormValid by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isCardNumberValid, isCvvValid, isMonthValid, isYearValid) {
        isFormValid = isCardNumberValid && isCvvValid && isMonthValid && isYearValid
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .imePadding()
    ) {

        CardInputField(
            title = stringResource(id = R.string.card_holder),
            placeholder = "Name",
            value = cardHolderName,
            inputType = KeyboardType.Text,
            action = ImeAction.Next,
            onValueChange = onCardHolderNameChange
        )

        CardInputField(
            title = stringResource(id = R.string.card_number),
            placeholder = "**** **** **** ****",
            value = cardNumber,
            inputType = KeyboardType.Number,
            action = ImeAction.Next,
            onValueChange = { newValue ->
                if (newValue.text.length <= Card.CARD_NUMBER_MAX.toInt()) {

                    val digitsOnly = newValue.text.replace(" ", "")
                    val formattedValue = digitsOnly.chunked(4).joinToString(" ")

                    unformattedNumber = digitsOnly

                    // Validate card number based on length
                    isCardNumberValid =
                        digitsOnly.length in Card.CARD_NUMBER_MIN.toInt()..Card.CARD_NUMBER_MAX.toInt()

                    // Adjust cursor position for formatted text
                    val originalCursorPosition = newValue.selection.start
                    val spacesBeforeCursor =
                        newValue.text.take(originalCursorPosition).count { it == ' ' }
                    val newCursorPosition =
                        originalCursorPosition + formattedValue.take(originalCursorPosition)
                            .count { it == ' ' } - spacesBeforeCursor

                    val adjustedCursorPosition =
                        newCursorPosition.coerceIn(0, formattedValue.length)

                    onCardNumberChange(
                        newValue.copy(
                            text = formattedValue,
                            selection = TextRange(adjustedCursorPosition)
                        )
                    )

                    // Recalculate form validity on every card number change
                    isFormValid = isCardNumberValid && isCvvValid && isMonthValid && isYearValid
                }
            }
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardInputField(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.cvv),
                placeholder = "000",
                inputType = KeyboardType.Number,
                action = ImeAction.Next,
                value = cvc,
                onValueChange = { newValue ->
                    if (newValue.text.length <= Card.CVV_MAX.toInt()) {
                        val newText = newValue.text.replace(" ", "")
                        val selection = newValue.selection

                        onCvcChange(
                            TextFieldValue(
                                text = newText,
                                selection = TextRange(selection.start.coerceIn(0, newText.length))
                            )
                        )

                        cvv = newText
                    }
                },
            )
//            if (!isCvvValid) {
//                Text("Invalid CVV", color = Color.Red)
//            }

            Spacer(modifier = Modifier.width(16.dp))
            CardInputField(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.expiry),
                placeholder = "MM/YY",
                inputType = KeyboardType.Number,
                action = ImeAction.Done,
                value = expiryDate,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.text.replace("/", "").take(4)

                    // Extract month and year from the digitsOnly
                    month = digitsOnly.take(2)
                    year = digitsOnly.drop(2)

                    // Get current month and year
                    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1 // Calendar.MONTH is 0-indexed
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100 // Last two digits of the year

                    // Validate month input
                    val isMonthInputValid = month.toIntOrNull()?.let { enteredMonth ->
                        enteredMonth in 1..12 // Only allow months from 01 to 12
                    } ?: false

                    // Validate year
                    isYearValid = year.length == 2 && year.toIntOrNull()?.let { enteredYear ->
                        enteredYear >= currentYear // Year should be current or in the future
                    } ?: false

                    // Validate month and date only if month is valid
                    val isDateValid = if (isMonthInputValid) {
                        month.toIntOrNull()?.let { enteredMonth ->
                            when {
                                // If the year is less than the current year, date is invalid
                                year.toIntOrNull() ?: 0 < currentYear -> false
                                // If the entered year is the same as the current year, check the month
                                year.toIntOrNull() == currentYear -> enteredMonth >= currentMonth
                                // If the entered year is in the future, date is valid
                                else -> true
                            }
                        } ?: false
                    } else {
                        false // If the month input is invalid, date valid is false
                    }

                    // Update isMonthValid based on the corrected date validation
                    isMonthValid = isMonthInputValid && isDateValid // Ensure month is valid

                    // Update the overall form validation
                    isFormValid = isCardNumberValid && isCvvValid && isMonthValid && isYearValid

                    // Ensure correct formatting and pre-append 0 if necessary
                    val validatedMonth = when {
                        month.isEmpty() -> ""
                        month.toIntOrNull() == null || month.toInt() > 12 -> "${month.first()}"
                        month.first() > '1' -> "0$month" // Prepend 0 if the month starts with 2 or greater
                        else -> month
                    }
                    val formattedValue = listOf(validatedMonth, year).filter { it.isNotEmpty() }
                        .joinToString("/")

                    // Ensure correct cursor position
                    val originalCursorPosition = newValue.selection.start
                    val newCursorPosition = if (validatedMonth.length == 2 && originalCursorPosition <= 2) {
                        2 // If the month is two digits, place cursor at the end of the month
                    } else if (originalCursorPosition > 2) {
                        // If the cursor is after the month, adjust for the "/" character
                        originalCursorPosition + 1
                    } else {
                        originalCursorPosition // Keep the original cursor position if in the valid range
                    }

                    // Check if the input value is valid before updating
                    if (isMonthInputValid || year.isEmpty()) { // Allow updating only if month is valid or year is empty
                        onExpiryDateChange(
                            newValue.copy(
                                text = formattedValue,
                                selection = TextRange(newCursorPosition.coerceIn(0, formattedValue.length))
                            )
                        )
                    }
                }


            )
        }


        Spacer(modifier = Modifier.height(26.dp))
        Button(

            onClick = {
                val order = xpressCardPay?._order
                val payer = xpressCardPay?._payer
                isButtonClicked = true
                if (order != null && payer != null) {
                    val card =
                        EdfaPgCard(unformattedNumber, month.toInt(), year.toInt() + 2000, cvv)
                    PaymentActivity.saleResponse = null
                    isResponseReceived = false

                    EdfaPgSdk.Adapter.SALE.execute(
                        order = order,
                        card = card,
                        payer = payer,
                        termUrl3ds = EdfaPgUtil.ProcessCompleteCallbackUrl,
                        options = null,
                        auth = false,
                        callback = handleSaleResponse(
                            CardTransactionData(
                                order,
                                payer,
                                card,
                                null
                            ),
                            { response, cardData ->
                                PaymentActivity.saleResponse = response
                                isResponseReceived = true
                                val intent = EdfaPgSaleWebRedirectActivity.intent(context = activity!!, cardData)
                                sale3dsRedirectLauncher.launch(intent)
                            },
                            { error ->
                                isResponseReceived = true
                                if (error != null) {
                                    println("Transaction failed: ${error.message}")
                                } else {
                                    println("Transaction was declined.")
                                }
                            }
                        )
                    )
                }
            },
            enabled = isFormValid && (isResponseReceived || !isButtonClicked),

            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.color_main)),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(text = stringResource(id = R.string.pay), color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.clickable {
                // navController.popBackStack()
            }
        ) {
            Footer()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CardInputField(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.card_holder),
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
                val localFocusManager = LocalFocusManager.current
                val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
                val focusRequester = remember { FocusRequester() }
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
                        .focusable(true)
                        .focusRequester(focusRequester)
                        .onFocusChanged { if(it.isFocused) localSoftwareKeyboardController?.hide() }
                        .focusable(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = inputType,
                        imeAction = action
                    ),
                    keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
                    cursorBrush = SolidColor(Color(0xFF2C3246)), // Cursor color
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }
                    }
                )
            }
        }
    }

}