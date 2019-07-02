package studio.attect.framework666.simple

/**
 * 文件安全写入环境回调
 * @see #Context.makeSureFileWriteEnvironment
 *
 * @author Attect
 */
abstract class FileSafeWriteCallback {
    /**
     * 空间不足
     */
    open fun spaceNotEnough(alsoNeedSize: Long) {}

    /**
     * 路径目录创建失败
     */
    open fun pathDirCreateFailed(path: String) {}

    /**
     * 路径不是一个文件夹
     */
    open fun pathIsNotDirectory(path: String) {}

    /**
     * 指定文件无法写入
     */
    open fun fileCannotWrite() {}

    /**
     * 意料之外的错误
     */
    open fun unexpectedError() {}

    /**
     * 操作成功
     */
    open fun success() {}
}