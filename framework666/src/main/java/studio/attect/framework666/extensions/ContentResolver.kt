package studio.attect.framework666.extensions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import studio.attect.framework666.cache.CacheManager
import studio.attect.framework666.simple.FileSafeWriteCallback
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream

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

/**
 * 通过ContentResolver将指定uri的数据保存到自身App的缓存目录下
 * 以进行各种读写操作运算
 * 写入规则遵循
 * @see Context.makeSureFileWriteEnvironment
 * 且支持[fileCopyCallback]回调
 * 通过[function]的File对象几乎拥有所有操作权限，但在使用结束后尽量立即删除。
 * 得到的File对象即使不及时删除，也会在下次App启动时自动删除
 * [function]中第一个参数为获取到的资源文件名称，第二个为可操作文件对象
 *
 * 需要注意的是：流的复制是在子线程中进行的，但[function]的调用是在方法的调用线程上执行的
 */
fun ContentResolver.getUriAsLocalFile(context: Context, uri: Uri, fileCopyCallback: FileSafeWriteCallback? = null, function: (String, File) -> Unit) {
    uri.metaData(this)?.let { (fileName, size) ->
        if (CacheManager.ensureContentResolverCacheDir(context)) {
            val (copyFile, _) = CacheManager.generateContentResolverFileTarget(context)
            val mCopyCallback = object : FileSafeWriteCallback() {
                override fun spaceNotEnough(alsoNeedSize: Long) {
                    fileCopyCallback?.spaceNotEnough(alsoNeedSize)
                }

                override fun pathDirCreateFailed(path: String) {
                    fileCopyCallback?.pathDirCreateFailed(path)
                }

                override fun pathIsNotDirectory(path: String) {
                    fileCopyCallback?.pathIsNotDirectory(path)
                }

                override fun fileCannotWrite() {
                    fileCopyCallback?.fileCannotWrite()
                }

                override fun unexpectedError() {
                    fileCopyCallback?.unexpectedError()
                }

                override fun success() {
                    fileCopyCallback?.success()
                    function.invoke(fileName, copyFile)
                }
            }
            context.makeSureFileWriteEnvironment(copyFile, size, mCopyCallback) {
                readFileFromUri(uri) { fileDescriptor ->
                    val fileInputStream = FileInputStream(fileDescriptor)
                    val fileOutputStream = FileOutputStream(copyFile)
                    fileInputStream.copyTo(fileOutputStream)
                }
            }
        }
    }
}