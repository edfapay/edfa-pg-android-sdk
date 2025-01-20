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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import com.edfapg.sdk.R

@Composable
fun Card1UI(
    cardNumber: String,
    cardHolderName: String,
    expiryDate: String,
    cvc: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(180.dp)
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
            Text(
                text = stringResource(id = R.string.txt_desc),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontSize = 7.sp,
                modifier = Modifier
                    .padding(start = 20.dp, top = 5.dp),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(50.dp)
            ) {

            }
            Spacer(modifier = Modifier.weight(1f))
            Column() {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 5.dp),
                    text = cardNumber,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    text = cardHolderName,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                    , verticalAlignment = Alignment.CenterVertically
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
fun CardForm(
    cardNumber: TextFieldValue,
    cardHolderName: TextFieldValue,
    expiryDate: TextFieldValue,
    cvc: TextFieldValue
) {

    Card1UI(
        cardNumber = cardNumber.text.ifEmpty { "**** **** **** ****" },
        cardHolderName = cardHolderName.text.ifEmpty { stringResource(id = R.string.txt_card_holdername)},
        expiryDate = expiryDate.text.ifEmpty { "00/00" },
        cvc = cvc.text.ifEmpty { "0000" }
    )
}