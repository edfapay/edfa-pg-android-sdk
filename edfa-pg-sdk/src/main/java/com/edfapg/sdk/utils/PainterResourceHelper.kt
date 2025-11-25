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
    
    // Validate synchronously during composition (before calling painterResource)
    val validationResult: Pair<Boolean, String?> = remember(id) {
        val resourceName = try {
            context.resources.getResourceEntryName(id)
        } catch (e: Exception) {
            Log.e("EdfaPgSDK", "═══════════════════════════════════════════════════════════", e)
            Log.e("EdfaPgSDK", "❌ DRAWABLE RESOURCE ERROR", e)
            Log.e("EdfaPgSDK", "Resource ID: $id", e)
            Log.e("EdfaPgSDK", "Error: Resource not found in your app's resources", e)
            Log.e("EdfaPgSDK", "Action: Check if the resource exists in your res/drawable folder", e)
            Log.e("EdfaPgSDK", "═══════════════════════════════════════════════════════════", e)
            return@remember Pair(false, null)
        }
        
        val drawable = try {
            context.resources.getDrawable(id, null)
        } catch (e: Exception) {
            Log.e("EdfaPgSDK", "═══════════════════════════════════════════════════════════", e)
            Log.e("EdfaPgSDK", "❌ DRAWABLE RESOURCE ERROR", e)
            Log.e("EdfaPgSDK", "Resource Name: $resourceName", e)
            Log.e("EdfaPgSDK", "Resource ID: $id", e)
            Log.e("EdfaPgSDK", "Error: Failed to load drawable resource", e)
            Log.e("EdfaPgSDK", "Action: Verify the resource file is valid and accessible", e)
            Log.e("EdfaPgSDK", "═══════════════════════════════════════════════════════════", e)
            return@remember Pair(false, resourceName)
        }
        
        if (drawable == null) {
            Log.w("EdfaPgSDK", "═══════════════════════════════════════════════════════════")
            Log.w("EdfaPgSDK", "⚠️ DRAWABLE RESOURCE WARNING")
            Log.w("EdfaPgSDK", "Resource Name: $resourceName")
            Log.w("EdfaPgSDK", "Resource ID: $id")
            Log.w("EdfaPgSDK", "Issue: Drawable resource is null")
            Log.w("EdfaPgSDK", "Action: Check if the resource file exists and is properly formatted")
            Log.w("EdfaPgSDK", "═══════════════════════════════════════════════════════════")
            return@remember Pair(false, resourceName)
        }
        
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
            Log.w("EdfaPgSDK", "═══════════════════════════════════════════════════════════")
            Log.w("EdfaPgSDK", "⚠️ UNSUPPORTED DRAWABLE TYPE DETECTED")
            Log.w("EdfaPgSDK", "Resource Name: $resourceName")
            Log.w("EdfaPgSDK", "Resource ID: $id")
            Log.w("EdfaPgSDK", "Drawable Type: $className")
            Log.w("EdfaPgSDK", "Issue: Jetpack Compose does not support this drawable type")
            Log.w("EdfaPgSDK", "Supported Types: VectorDrawable (.xml with <vector>), PNG, JPG, WEBP")
            Log.w("EdfaPgSDK", "Unsupported Types: Shape, GradientDrawable, StateListDrawable, etc.")
            Log.w("EdfaPgSDK", "Solution: Convert $resourceName to PNG/VectorDrawable format")
            Log.w("EdfaPgSDK", "Impact: This resource will not be displayed (app will continue to work)")
            Log.w("EdfaPgSDK", "═══════════════════════════════════════════════════════════")
            return@remember Pair(false, resourceName)
        }
        
        Pair(true, resourceName)
    }

    val (isValid, resourceName) = validationResult


    if (!isValid) {
        Log.i("EdfaPgSDK", "ℹ️ Skipped loading resource: $resourceName (ID: $id) - Resource is invalid or unsupported")
        return null
    }

    // Only call painterResource if validation passed
    // This should be safe now, but if it still throws, the global handler will catch it
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

