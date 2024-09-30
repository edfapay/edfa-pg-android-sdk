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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun Card3UI(
    navController: NavController,
    cardNumber: String,
    cardHolderName: String,
    expiryDate: String,
    cvc: String,
    xpressCardPay: EdfaCardPay?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(210.dp)
            .heightIn(max = Dp.Unspecified, min = 100.dp)
            .background(
                Color(0xFF00A86B),
                shape = RoundedCornerShape(16.dp)
            )

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
            ) {
                xpressCardPay?._order?.let {
                    val amount = it.formattedAmount() // Get formatted amount from order
                    val currency = it.formattedCurrency() // Get formatted currency from order
                    TitleAmount(amount = amount, currency = currency)
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
                        painter = painterResource(id = R.drawable.visa_orange_icon),
                        contentDescription = "holographic"
                    )
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            text = cardHolderName.ifEmpty {stringResource(id = R.string.txt_card_holdername)},
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
                        )
                        Text(
                            text = cardNumber.ifEmpty { "**** **** **** ****" },
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black
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
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(id = R.string.txt_validate),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 8.sp,
                        lineHeight = 10.sp
                    )
                    Column {
                        Text(
                            text = stringResource(id = R.string.txt_month),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontSize = 8.sp
                        )
                        Text(
                            text = expiryDate,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.5f))

                    Column {
                        Text(
                            text = stringResource(id = R.string.cvv),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                        Text(
                            text = cvc,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center),
                            painter = painterResource(id = R.drawable.holographic),
                            contentDescription = "holographic"
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                        ) {
                            repeat(20) {
                                Row {
                                    Text(
                                        text = "Master Card",
                                        color = Color.White.copy(alpha = 0.2f),
                                        fontSize = 5.sp,
                                        modifier = Modifier.padding(2.dp),
                                        lineHeight = 2.sp

                                    )
                                    Text(
                                        text = "Master Card",
                                        color = Color.White.copy(alpha = 0.2f),
                                        fontSize = 5.sp,
                                        modifier = Modifier.padding(2.dp),
                                        lineHeight = 2.sp

                                    )
                                }
                            }

                        }
                    }

                }
            }


        }
    }
}


@Composable
fun TitleAmount(amount: String,currency:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Column {
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
    }
}