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
    var shouldSkip by remember(id) { mutableStateOf(false) }
    

    val resourceName = try {
        context.resources.getResourceEntryName(id)
    } catch (e: Exception) {
        Log.e("PainterResourceHelper", "Resource not found: $id", e)
        shouldSkip = true
        null
    }
    
    // Validate resource type in a side effect
    DisposableEffect(id) {
        if (!shouldSkip && resourceName != null) {
            isValidResource = try {
                val drawable = context.resources.getDrawable(id, null)
                if (drawable == null) {
                    false
                } else {
                    val className = drawable.javaClass.simpleName
                    // Check for unsupported drawable types that Compose can't handle
                    val isUnsupported = className.contains("Shape") || 
                                        className.contains("GradientDrawable") ||
                                        className.contains("StateListDrawable") ||
                                        className.contains("LayerDrawable") ||
                                        className.contains("InsetDrawable") ||
                                        className.contains("ClipDrawable") ||
                                        className.contains("RotateDrawable") ||
                                        className.contains("ScaleDrawable") ||
                                        className.contains("AnimationDrawable")
                    
                    if (isUnsupported) {
                        Log.w("PainterResourceHelper", "Unsupported drawable type for Compose: $className (resource: $id, name: $resourceName)")
                        shouldSkip = true
                        false
                    } else {
                        true
                    }
                }
            } catch (e: Exception) {
                Log.e("PainterResourceHelper", "Invalid drawable resource: $id", e)
                shouldSkip = true
                false
            }
        }
        onDispose { }
    }

    if (shouldSkip || isValidResource == false) {
        Log.w("PainterResourceHelper", "Attempting to load invalid/unsupported resource: $id ($resourceName) - exception will be caught by global handler")
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

