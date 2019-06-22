package studio.attect.framework666.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 * Drawable转Bitmap
 */
fun Drawable.toBitmap(): Bitmap {
    val width = intrinsicWidth
    val height = intrinsicHeight
    val config = if (opacity != PixelFormat.OPAQUE) {
        Bitmap.Config.ARGB_8888
    } else {
        Bitmap.Config.RGB_565
    }
    val bitmap = Bitmap.createBitmap(width, height, config)
    //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
    val canvas = Canvas(bitmap)
    setBounds(0, 0, width, height)
    draw(canvas)

    return bitmap
}