package studio.attect.framework666.extensions

import android.content.ContentResolver
import android.net.Uri
import java.io.FileDescriptor

/**
 * 通过ContentResolver读取一个Uri
 * @see <a href="https://developer.android.google.cn/guide/topics/providers/document-provider#open-client">使用存储访问框架打开文件
</a>
 */
fun <T> ContentResolver.readFileFromUri(uri: Uri, mode: String = "r", function: (FileDescriptor) -> T): T? {
    openFileDescriptor(uri, mode).use { parcelFileDescriptor ->
        parcelFileDescriptor?.fileDescriptor?.let {
            return function.invoke(it)
        }
    }
    return null
}