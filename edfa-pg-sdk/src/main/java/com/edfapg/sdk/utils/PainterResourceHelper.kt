package com.edfapg.sdk.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import android.util.Log

@Composable
fun safePainterResource(id: Int): Painter? {
    val context = LocalContext.current
    var isValidResource by remember(id) { mutableStateOf<Boolean?>(null) }
    
    // Validate resource in a side effect
    DisposableEffect(id) {
        isValidResource = try {
            val drawable = context.resources.getDrawable(id, null)
            if (drawable == null) {
                false
            } else {
                val className = drawable.javaClass.simpleName
                val isUnsupported = className.contains("Shape") || 
                                    className.contains("Gradient") ||
                                    className.contains("StateList") ||
                                    className.contains("LayerList")
                
                if (isUnsupported) {
                    Log.w("PainterResourceHelper", "Unsupported drawable type for Compose: $className (resource: $id)")
                    false
                } else {
                    true
                }
            }
        } catch (e: Exception) {
            Log.e("PainterResourceHelper", "Invalid drawable resource: $id", e)
            false
        }
        onDispose { }
    }


    if (isValidResource == false) {
        Log.w("PainterResourceHelper", "Loading potentially invalid resource: $id - this may crash")
    }
    
    return painterResource(id = id)
}

@Composable
fun SafeImage(
    id: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val painter = safePainterResource(id)
    if (painter != null) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}

