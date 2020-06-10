package studio.attect.framework666.extensions

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

/**
 * 从ContentProvider中得到的Uri中获取目标元数据
 */
fun Uri.metaData(contentResolver: ContentResolver): Pair<String, Long>? {
    contentResolver.query(this, null, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val displayName: String = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            val sizeIndex: Int = cursor.getColumnIndex(OpenableColumns.SIZE)
            val size: Long = if (!cursor.isNull(sizeIndex)) {
                cursor.getString(sizeIndex).toLongOrNull() ?: -1L
            } else {
                -1L
            }
            return Pair(displayName, size)
        }
    }
    return null
}