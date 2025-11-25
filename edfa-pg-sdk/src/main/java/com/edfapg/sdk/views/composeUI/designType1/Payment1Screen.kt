package com.example.paymentgatewaynew.payment1

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.edfapg.sdk.R
import com.edfapg.sdk.utils.CrashReproductionTest
import com.edfapg.sdk.utils.MyAppTheme
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Payment1Screen(
    navController: NavController,
    xpressCardPay: EdfaCardPay?,
    activity: Activity,
    sale3dsRedirectLauncher: ActivityResultLauncher<Intent>
) {
    val context = LocalContext.current
    var bottomSheetVisible by remember { mutableStateOf(true) }  // Initially visible

    // Bottom sheet state
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newState ->
            newState != SheetValue.Expanded
        }
    )

    // Handle back press logic using LocalOnBackPressedDispatcherOwner
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(key1 = backPressedDispatcher, key2 = bottomSheetState) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bottomSheetVisible) {
                    bottomSheetVisible = false
                } else {
                    (context as? Activity)?.finish()
                }
            }
        }

        backPressedDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }

    LaunchedEffect(bottomSheetVisible) {
        if (bottomSheetVisible && bottomSheetState.isVisible.not()) {
            bottomSheetState.show()
        } else if (!bottomSheetVisible) {
            bottomSheetState.hide()
        }
    }
    if (bottomSheetVisible) {
        ModalBottomSheet(
            modifier = Modifier
                .imePadding(),
            sheetState = bottomSheetState,
            containerColor = Color.White,
            onDismissRequest = { bottomSheetVisible = false },
        ) {
            val config = LocalConfiguration.current
            val halfHeight = (config.screenHeightDp * 0.8f).dp
            xpressCardPay?._order?.let {
                val amount = it.formattedAmount()
                val currency = it.formattedCurrency()
                MyAppTheme {
                    Column(Modifier
                        .heightIn(max = halfHeight)) {
                        Box {
                            TitleAmount(
                                amount = amount,
                                currency = currency
                            )
                        }
                        Payment1Form(
                            xpressCardPay = xpressCardPay,
                            activity = activity,
                            sale3dsRedirectLauncher = sale3dsRedirectLauncher
                        )
                    }
                }
            }
        }
    } else {
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
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth(),
            fontSize = 14.sp
        )
        Text(
            text = "$amount $currency",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}
