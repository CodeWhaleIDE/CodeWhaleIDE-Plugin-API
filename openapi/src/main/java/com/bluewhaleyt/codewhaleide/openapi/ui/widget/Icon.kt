package com.bluewhaleyt.codewhaleide.openapi.ui.widget

import android.graphics.Bitmap
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import coil.compose.AsyncImage

interface Icon

data class VectorIcon internal constructor(
    val imageVector: ImageVector
) : Icon

data class PainterIcon internal constructor(
    val painter: Painter
) : Icon

data class BitmapIcon internal constructor(
    val bitmap: ImageBitmap
) : Icon

data class CoilAsyncIcon internal constructor(
    val model: Any?
) : Icon

fun ImageVector.toIcon() = VectorIcon(this)

fun Painter.toIcon() = PainterIcon(this)

fun ImageBitmap.toIcon() = BitmapIcon(this)

fun Bitmap.toIcon() = BitmapIcon(this.asImageBitmap())

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    icon: Icon,
    contentDescription: String?,
    tint: Color = LocalContentColor.current
) {
    when (icon) {
        is VectorIcon -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is PainterIcon -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                painter = icon.painter,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is BitmapIcon -> {
            androidx.compose.material3.Icon(
                modifier = modifier,
                bitmap = icon.bitmap,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is CoilAsyncIcon -> {
            AsyncImage(
                modifier = modifier,
                model = icon.model,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(tint)
            )
        }
    }
}