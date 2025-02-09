package com.example.paymentgatewaynew.payment3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.edfapg.sdk.R
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.example.paymentgatewaynew.payment1.Card1UI
import com.example.paymentgatewaynew.payment2.Card2UI

@Composable
fun Card3UI(
    navController: NavController,
    cardNumber: String,
    cardHolderName: String,
    expiryDate: String,
    cvv: String,
    xpressCardPay: EdfaCardPay?
) {
    var scheme = '0'
    if (cardNumber.isNotEmpty() && cardNumber.first() == '4') {
        scheme = '4'
    } else if (cardNumber.isNotEmpty() && (cardNumber.first() == '5' || cardNumber.first() == '2')) {
        scheme = '5'
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(210.dp)
            .heightIn(max = Dp.Unspecified, min = 100.dp)
            .background(
                Color(0xFF2BD190),
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box {
                xpressCardPay?._order?.let {
                    val amount = it.formattedAmount() // Get formatted amount from order
                    val currency = it.formattedCurrency() // Get formatted currency from order
                    TitleAmount(amount = amount, currency = currency, expiryDate, cvv)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .offset(y = (25).dp) // Move the behind card upwards
                    .background(
                        Color(0xAA058961).copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .height(15.dp)
            ) {
                // Content of the behind white box
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .offset(y = (20).dp) // Move the behind card upwards
                    .background(
                        Color(0xFF22BB8D),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .height(50.dp)
            ) {
                // Content of the behind white box
            }

            Box(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .offset(y = (-20).dp) // Keep the front card in its original position
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(50.dp),
                        painter =
                        painterResource(
                            id = when (scheme) {
                                '4' -> R.drawable.visa_orange_icon
                                '5' -> R.drawable.ic_mastercard_logo
                                else -> R.drawable.vector_transparent // Default transparent icon

                            }
                        ),
                        contentDescription = "holographic"
                    )

                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            text = if (cardHolderName.isEmpty() || cardHolderName.first().isWhitespace()) {
                                stringResource(id = R.string.txt_card_holdername)
                            } else {
                                cardHolderName
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )
                        Text(
                            text = if (cardNumber.isEmpty() || cardNumber.first().isWhitespace()) {
                                "****  ****  ****  ****"
                            } else {
                                cardNumber
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )

                    }
                    Spacer(
                        modifier = Modifier.weight(0.3f)
                    )
                    Column(
                    ) {
                        Text(
                            text = stringResource(id = R.string.txt_validate),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 6.sp,
                            lineHeight = 6.sp
                        )
                        Text(
                            modifier = Modifier.width(50.dp)
                                .align(
                                    Alignment.CenterHorizontally
                                ),
                            text = if (expiryDate.isEmpty() || expiryDate.first().isWhitespace()) {
                                "MM/YY"
                            } else {
                                expiryDate
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black,
                            fontSize = 10.sp,

                        )
//                    Spacer(modifier = Modifier.weight(0.5f))
                    }
                    Column {
                        Text(
                            text = stringResource(id = R.string.cvv_code),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black,
                            fontSize = 6.sp,
                            lineHeight = 6.sp
                        )
                        Text(
                            modifier = Modifier.width(50.dp).align(
                                Alignment.CenterHorizontally
                            ),
                            text = if (cvv.isEmpty() || cvv.first().isWhitespace()) {
                                "0000"
                            } else {
                                cvv
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black,
                            fontSize = 10.sp,
                        )
                    }
                }
            }


            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(96.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {

            }
            Spacer(modifier = Modifier.weight(1f))
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = cardNumber,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = cardHolderName,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }


        }
    }
}


@Composable
fun TitleAmount(amount: String, currency: String, expiryDate: String, cvv: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.txt_total),
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 14.sp
            )
            Text(
                text = "$amount $currency",
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp),
                painter = painterResource(id = R.drawable.edfapay_logo_white),
                contentDescription = "holographic"
            )
        }


    }
}