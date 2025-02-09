package com.example.paymentgatewaynew.payment1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edfapg.sdk.R

@Composable
fun Card1UI(
    cardNumber: String,
    cardHolderName: String,
    expiryDate: String,
    cvc: String
) {
    var scheme = '0'
    if (cardNumber.isNotEmpty() && cardNumber.first() == '4') {
        scheme = '4'
    } else if (cardNumber.isNotEmpty() && (cardNumber.first() == '5' || cardNumber.first() == '2')) {
        scheme = '5'
    }

    val spaceBetween = 10.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(200.dp)
            .heightIn(max = Dp.Unspecified, min = 100.dp)
            .background(
                color = Color(0xFF2BD190), //0xFF108F68 --second color in figma
//                color = Color(0xFF00E6A0), //00E6A0 --second color in figma
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.9f) // 50% transparency for the image
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.card_map_bg), // Replace with your image resource
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp, end = 20.dp),
            ) {
                Row {
                    Image(
                        modifier = Modifier
                            .padding(top = spaceBetween)
                            .size(30.dp),
                        painter = painterResource(id = R.drawable.chip),
                        contentDescription = "holographic"
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(top = spaceBetween)
                            .size(50.dp),
                        painter = painterResource(id = R.drawable.edfapay_logo_white),
                        contentDescription = "holographic"
                    )
                }
                Text(
                    text = cardNumber,
                    color = Color.White
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(id = R.string.txt_validate),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 6.sp,
                        lineHeight = 6.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(50.dp),
                        text = expiryDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(id = R.string.cvv_code),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 6.sp,
                        lineHeight = 6.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(50.dp),
                        text = cvc,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                    )

                }
                Row {
                    Column {
                        Text(
                            modifier = Modifier
                                .padding(
                                    top = spaceBetween
                                ),
                            text = cardHolderName,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                        )
                        Text(
                            text = "-----------",
                            color = Color.White,
                            lineHeight = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center),
                            painter =
                            painterResource(
                                id = when (scheme) {
                                    '4' -> R.drawable.visa_white_icon
                                    '5' -> R.drawable.ic_mastercard_logo
                                    else -> R.drawable.vector_transparent // Default transparent icon

                                }
                            ),
                            contentDescription = "holographic"
                        )
                    }
                }

            }
        }

}



@Composable
fun CardForm(
    cardNumber: TextFieldValue,
    cardHolderName: TextFieldValue,
    expiryDate: TextFieldValue,
    cvc: TextFieldValue
) {
    println("Payment1Form ${cardHolderName}")

    Card1UI(
        cardNumber = if (cardNumber.text.isEmpty() || cardNumber.text.first().isWhitespace()) {
            "****  ****  ****  ****"
        } else {
            cardNumber.text
        },
        cardHolderName = if (cardHolderName.text.isEmpty() || cardHolderName.text.first().isWhitespace()) {
            stringResource(id = R.string.txt_card_holdername)
        } else {
            cardHolderName.text
        },
        expiryDate = if (expiryDate.text.isEmpty() || expiryDate.text.first().isWhitespace()) {
            "MM/YY"
        } else {
            expiryDate.text
        },
        cvc = if (cvc.text.isEmpty() || cvc.text.first().isWhitespace()) {
            "0000"
        } else {
            cvc.text
        }
    )

}