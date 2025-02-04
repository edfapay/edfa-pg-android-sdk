package com.example.paymentgatewaynew.payment2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.edfapg.sdk.R

@Composable
fun Card2UI(
    cardNumber: String,
    cardHolderName: String,
    expiryDate: String,
    cvc: String
) {
    val redCard by remember { mutableStateOf(false) }
    val blueCard by remember { mutableStateOf(true) }
    val greenCard by remember { mutableStateOf(false) }

    // State for padding values
    var redCardPadding by remember { mutableStateOf(0.dp) }
    var blueCardPadding by remember { mutableStateOf(0.dp) }
    var greenCardPadding by remember { mutableStateOf(0.dp) }

    var redCardHeight by remember { mutableStateOf(0.dp) }
    var blueCardHeight by remember { mutableStateOf(0.dp) }
    var greenCardHeight by remember { mutableStateOf(0.dp) }

    val firstBehindCardPadding = 30.dp
    val secondBehindCardPadding = 52.dp

    val frontCardHeight by remember { mutableStateOf(214.dp) }
    val firstBackCardHeight by remember { mutableStateOf(205.dp) }
    val secondBackCardHeight by remember { mutableStateOf(220.dp) }



    if (redCard) {
        blueCardPadding = secondBehindCardPadding
        greenCardPadding = firstBehindCardPadding
        redCardHeight = frontCardHeight
        greenCardHeight = firstBackCardHeight
        blueCardHeight = secondBackCardHeight

    } else if (blueCard) {
        greenCardPadding = firstBehindCardPadding
        redCardPadding = secondBehindCardPadding
    } else if (greenCard) {
        blueCardPadding = firstBehindCardPadding
        redCardPadding = secondBehindCardPadding
    }
    val cardHeight = 214.dp

    Box(
        modifier = Modifier.width(200.dp)
    ) {
        // Red Card
        Box(
            modifier = Modifier
                .height(when{
                    redCard -> 214.dp
                    greenCard -> 195.dp
                    blueCard -> 205.dp
                    else -> cardHeight
                })
                .padding(start = redCardPadding)
                .fillMaxWidth()
                .offset(y = if (redCard) 0.dp else 40.dp)
                .zIndex(if (redCard) 3f else 1f)
                .background(
                    Color(0xFFF86939),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 16.dp
                    )
                )
        ) {
            CardView(cardNumber, cardHolderName, expiryDate, cvc)
        }

        // Blue Card
        Box(
            modifier = Modifier
                .height(when{
                    blueCard -> 214.dp
                    greenCard -> 195.dp
                    redCard -> 205.dp
                    else -> cardHeight
                })
                .fillMaxWidth()
                .padding(start = blueCardPadding)
                .offset(y = if (blueCard) 0.dp else 30.dp)
                .zIndex(if (blueCard) 3f else 1f)
                .background(
                    Color(0xFF2979F2),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 16.dp
                    )
                )
        ) {
            CardView(cardNumber, cardHolderName, expiryDate, cvc)
        }
        // Green Card
        Box(
            modifier = Modifier
                .height(when{
                    greenCard -> 214.dp
                    blueCard -> 210.dp
                    redCard -> 205.dp
                    else -> cardHeight
                })
                .fillMaxWidth()
                .padding(start = greenCardPadding)
                .offset(y = if (greenCard) 0.dp else 20.dp)
                .zIndex(if (greenCard) 3f else 1f)
                .background(
                    Color(0xFF73AD31),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 16.dp
                    )
                )
        ) {
            CardView(cardNumber, cardHolderName, expiryDate, cvc)
        }
    }
}
@Composable
fun CardView(cardNumber: String, cardHolderName: String, expiryDate: String, cvc: String) {
    Column {
        Text(
            text = stringResource(id = R.string.txt_desc),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontSize = 7.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp),
            maxLines = 1
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .height(40.dp)
        )
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
            }
        }
    }
}

@Composable
fun Card2Form(
    cardNumber: TextFieldValue,
    cardHolderName: TextFieldValue,
    expiryDate: TextFieldValue,
    cvc: TextFieldValue
) {
    Card2UI(
        cardNumber = cardNumber.text.ifEmpty { "**** **** **** ****" },
        cardHolderName = cardHolderName.text.ifEmpty { stringResource(id = R.string.txt_card_holdername)},
        expiryDate = expiryDate.text.ifEmpty { "00/00" },
        cvc = cvc.text.ifEmpty { "000" }
    )
}
