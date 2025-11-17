package com.edfapg.sdk.core

import Footer
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
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
import com.edfapg.sdk.PaymentActivity
import com.edfapg.sdk.R
import com.edfapg.sdk.model.request.card.EdfaPgCard
import com.edfapg.sdk.toolbox.EdfaPgUtil
import com.edfapg.sdk.toolbox.EdfaPgValidation.Card
import com.edfapg.sdk.views.edfacardpay.CardTransactionData
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.edfapg.sdk.views.edfacardpay.EdfaPgSaleWebRedirectActivity
import kotlinx.coroutines.delay
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
    var isYearValid by remember { mutableStateOf(false) }

    var isFormValid by remember {
        mutableStateOf(false)
    }

    val (cardHolderFocus, cardNumberFocus, cvcFocus, expiryDateFocus) = remember { List(4) { FocusRequester() } }

    LaunchedEffect(isCardNumberValid, isCvvValid, isMonthValid, isYearValid) {
        isFormValid = isCardNumberValid && isCvvValid && isMonthValid && isYearValid
        println("isFormValid: $isFormValid isCardNumberValid: $isCardNumberValid isCvvValid: $isCvvValid isMonthValid: $isMonthValid isYearValid: $isYearValid")
        println("inCardInputForm:: GlobalValid: isCardNumberValid: $isCardNumberValid isCvvValid: $isCvvValid isMonthValid: $isMonthValid isYearValid: $isYearValid")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .imePadding()
    ) {
        LaunchedEffect(Unit) {
//            delay(300)  // Small delay to allow composition
            cardHolderFocus.requestFocus()
        }

        CardInputField(
            title = stringResource(id = R.string.card_holder),
            placeholder = "Name",
            newValue = cardHolderName,
            inputType = KeyboardType.Text,
            action = ImeAction.Next,
            focusRequester = cardHolderFocus,
            onValueChange = { newValue ->
                // Enforce a maximum length of 20 characters
                val truncatedValue = if (newValue.text.length > 20) {
                    newValue.copy(
                        text = newValue.text.take(20), // Truncate to 20 characters
                        selection = TextRange(20) // Move cursor to the end
                    )
                } else {
                    newValue // Use the original value if within the limit
                }

                println(
                    "CardHolderName: ${truncatedValue.text}"
                )
                // Update the card holder name
                onCardHolderNameChange(truncatedValue)
            }
        )

        CardInputField(
            title = stringResource(id = R.string.card_number),
            placeholder = "**** **** **** ****",
            newValue = cardNumber,
            inputType = KeyboardType.Number,
            action = ImeAction.Next,
            focusRequester = cardNumberFocus,
            onValueChange = { newValue ->
                val rawDigits = newValue.text.replace("\\D".toRegex(), "") // Extract only digits

                if (rawDigits.length <= 16) { // Allow only 16 digits
                    val formattedValue = rawDigits.chunked(4).joinToString(" ") // Format as "**** **** **** ****"

                    unformattedNumber = rawDigits
                    isCardNumberValid = rawDigits.length in 12..16

                    println("inCardInputForm::isCardNumberValid: ${rawDigits.length} :: $isCardNumberValid")

                    val originalCursorPosition = newValue.selection.start
                    val spacesBeforeCursor = newValue.text.take(originalCursorPosition).count { it == ' ' }
                    val newCursorPosition =
                        originalCursorPosition + formattedValue.take(originalCursorPosition)
                            .count { it == ' ' } - spacesBeforeCursor

                    val adjustedCursorPosition = newCursorPosition.coerceIn(0, formattedValue.length)

                    // Handle clearing the input properly
                    onCardNumberChange(
                        if (rawDigits.isEmpty()) {
                            TextFieldValue("") // Ensures the field is properly cleared
                        } else {
                            TextFieldValue(
                                text = formattedValue,
                                selection = TextRange(adjustedCursorPosition)
                            )
                        }
                    )

                    // Recalculate form validity
                    isFormValid = isCardNumberValid && isCvvValid && isMonthValid
                    println("inCardInputForm: isFormValid : $isFormValid")
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
                newValue = cvc,
                focusRequester = cvcFocus,
                onValueChange = { newValue ->
                    // Remove all non-digit characters
                    val digitsOnly = newValue.text.replace("\\D".toRegex(), "")

                    // Always place cursor at end after modification
                    onCvcChange(
                        TextFieldValue(
                            text = digitsOnly.take(Card.CVV_MAX.toInt()),
                            selection = TextRange(digitsOnly.length.coerceAtMost(Card.CVV_MAX.toInt()))
                        )
                    )

                    cvv = digitsOnly.take(Card.CVV_MAX.toInt())
                }
            )

            Spacer(modifier = Modifier.width(16.dp))
            CardInputField(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.expiry),
                placeholder = "MM/YY",
                inputType = KeyboardType.Number,
                action = ImeAction.Done,
                newValue = expiryDate,
                focusRequester = expiryDateFocus,
                onValueChange = { newValue ->
                    // Remove all non-digit characters
                    val digitsOnly = newValue.text.replace("\\D".toRegex(), "").take(4)

                    // Extract month and year from the digits
                    month = digitsOnly.take(2)
                    year = digitsOnly.drop(2)

                    // Get current month and year
                    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1 // Calendar.MONTH is 0-based
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100 // Last two digits of year

                    // Validate month input
                    val isMonthInputValid = month.toIntOrNull()?.let { enteredMonth ->
                        enteredMonth in 1..12 // Ensure months are between 01-12
                    } ?: false

                    // Validate year
                    isYearValid = year.length == 2 && year.toIntOrNull()?.let { enteredYear ->
                        enteredYear >= currentYear // Year should be current or in the future
                    } ?: false


                    val isDateValid = if (isMonthInputValid) {
                        month.toIntOrNull()?.let { enteredMonth ->
                            when {
                                year.toIntOrNull() ?: 0 < currentYear -> false
                                year.toIntOrNull() == currentYear -> enteredMonth >= currentMonth
                                else -> true
                            }
                        } ?: false
                    } else {
                        false
                    }

                    isMonthValid = isMonthInputValid && isDateValid
                    isFormValid = isCardNumberValid && isCvvValid && isMonthValid

                    // Format month correctly (prepend 0 if necessary)
                    val validatedMonth = when {
                        month.isEmpty() -> ""
                        month.toIntOrNull() == null || month.toInt() > 12 -> "${month.first()}"
                        month.first() > '1' -> "0$month"
                        else -> month
                    }

                    // Format as MM/YY
                    val formattedValue = listOf(validatedMonth, year)
                        .filter { it.isNotEmpty() }
                        .joinToString("/")

                    // Ensure correct cursor position
                    val originalCursorPosition = newValue.selection.start
                    val newCursorPosition = when {
                        validatedMonth.length == 2 && originalCursorPosition <= 2 -> 2
                        originalCursorPosition > 2 -> originalCursorPosition + 1 // Adjust for '/'
                        else -> originalCursorPosition
                    }.coerceIn(0, formattedValue.length)

                    // Only update if input is valid
                    if (isMonthInputValid || year.isEmpty()) {
                        onExpiryDateChange(
                            newValue.copy(
                                text = formattedValue,
                                selection = TextRange(newCursorPosition)
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
                val recurring = xpressCardPay?._recurring
                val extras = xpressCardPay?._extras
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
                        extras = extras,
                        termUrl3ds = EdfaPgUtil.ProcessCompleteCallbackUrl,
                        options = recurring,
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
                                val intent = EdfaPgSaleWebRedirectActivity.intent(
                                    context = activity!!,
                                    cardData
                                )
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

@Composable
fun CardInputField(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.card_holder),
    placeholder: String = "Name",
    newValue: TextFieldValue,
    inputType: KeyboardType,
    action: ImeAction = ImeAction.Next,
    onValueChange: (TextFieldValue) -> Unit,
    focusRequester: FocusRequester = remember { FocusRequester() }
) {

    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = 5.dp)
            .background(
                Color(0xFFF1F4F8),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(verticalArrangement = Arrangement.spacedBy((-15).dp)) {
            // Title
            Text(
                text = title,
                color = Color(0xFF8F9BB3),
                style = MaterialTheme.typography.labelSmall,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp),
            )

            // Input area
            TextField(
                value = newValue,
                onValueChange = { newTextValue ->
                    onValueChange(newTextValue)
                },
                textStyle = LocalTextStyle.current.copy(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = inputType,
                    imeAction = action
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedPlaceholderColor = Color(0xFF8F9BB3),
                    unfocusedPlaceholderColor = Color(0xFF8F9BB3),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            onValueChange(newValue.copy(
                                selection = TextRange(newValue.text.length)
                            ))
                        }
                    }
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onValueChange(newValue.copy(
                                selection = TextRange(newValue.text.length)
                            ))
                        }
                    },
                trailingIcon = {
                    if (newValue.text.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            modifier = Modifier
                                .clickable {
                                    onValueChange(TextFieldValue("", TextRange.Zero))
                                }
                                .padding(end = 2.dp)
                                .size(20.dp),
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    }
}
