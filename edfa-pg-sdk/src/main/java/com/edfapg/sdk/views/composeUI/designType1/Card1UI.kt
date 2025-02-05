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
import androidx.compose.ui.graphics.Color
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

    val spaceBetween = 10.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(200.dp)
            .heightIn(max = Dp.Unspecified, min = 100.dp)
            .background(
                Color(0xFF73AD31),
                shape = RoundedCornerShape(16.dp)
            )
    ) {

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
                Column{
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
                        painter = painterResource(id = R.drawable.visa_white_icon),
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
    Card1UI(
        cardNumber = cardNumber.text.ifEmpty { "****  ****  ****  ****" },
        cardHolderName = cardHolderName.text.ifEmpty {
            stringResource(id = R.string.txt_card_holdername) },
        expiryDate = expiryDate.text.ifEmpty { "MM/YY" },
        cvc = cvc.text.ifEmpty { "0000" }
    )
}