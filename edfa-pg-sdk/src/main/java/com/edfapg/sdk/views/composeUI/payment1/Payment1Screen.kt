package com.example.paymentgatewaynew.payment1
import android.app.Activity
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edfapg.sdk.R
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Payment1Screen(navController: NavController,xpressCardPay: EdfaCardPay?,activity: Activity,sale3dsRedirectLauncher: ActivityResultLauncher<Intent>) {
    val context = LocalContext.current
    var bottomSheetVisible by remember { mutableStateOf(true) }  // Initially visible

    // Bottom sheet state
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newState ->
            newState != SheetValue.Hidden
        }
    )

    // Handle back press logic using LocalOnBackPressedDispatcherOwner
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(key1 = backPressedDispatcher, key2 = bottomSheetState) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bottomSheetVisible) {
                    // First press: Close the bottom sheet
                    bottomSheetVisible = false
                } else {
                    // If the bottom sheet is already closed, finish the activity
                    (context as? Activity)?.finish()
                }
            }
        }

        backPressedDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }

    // Only show the ModalBottomSheet if it's marked as visible
    if (bottomSheetVisible) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(0.9f),
            sheetState = bottomSheetState,
            containerColor = Color.White,
            onDismissRequest = { bottomSheetVisible = false }
        ) {
            xpressCardPay?._order?.let {
                val amount = it.formattedAmount() // Get formatted amount from order
                val currency = it.formattedCurrency() // Get formatted currency from order
                Column {
                    Box(
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            }
                    ) {
                        TitleAmount(amount = amount, currency = currency)  // Pass data to TitleAmount
                    }
                    Payment1Form(xpressCardPay = xpressCardPay, activity = activity, sale3dsRedirectLauncher = sale3dsRedirectLauncher)
                }
            }

        }
    } else {
        // If the bottom sheet is not visible, immediately finish the activity
        LaunchedEffect(Unit) {
            (context as? Activity)?.finish()
        }
    }
}

@Composable
fun TitleAmount(amount: String, currency: String) {
    Column {
        Text(
            text = stringResource(id = R.string.txt_total),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            fontSize = 14.sp
        )
        Text(
            text = "$amount $currency",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}