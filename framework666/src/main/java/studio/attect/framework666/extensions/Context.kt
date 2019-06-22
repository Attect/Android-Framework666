package studio.attect.framework666.extensions

import android.app.ActivityManager
import android.content.Context

/**
 * 获得进程名
 * 不一定成功
 * 不使用反射
 */
val Context.progressName: String?
    get() {
        val pid = android.os.Process.myPid()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager? ?: return null
        val progress = activityManager.runningAppProcesses ?: return null
        progress.forEach {
            if (it.pid == pid) return it.processName
        }
        return null
    }