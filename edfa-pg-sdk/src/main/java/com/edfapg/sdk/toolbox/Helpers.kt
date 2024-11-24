package com.edfapg.sdk.toolbox

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun delayAtMain(millis:Long, callback: () -> Unit){
    CoroutineScope(Dispatchers.Main).launch {
        delay(millis)
        callback()
    }
}

fun delayAtIO(millis:Long, callback: () -> Unit){
    CoroutineScope(Dispatchers.IO).launch {
        delay(millis)
        callback()
    }
}