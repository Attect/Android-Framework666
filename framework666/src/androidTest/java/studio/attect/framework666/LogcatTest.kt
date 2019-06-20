package studio.attect.framework666

import org.junit.Test

import org.junit.Assert.*

class LogcatTest {

    @Test
    fun d() {
        Logcat.d("debug", "debug level string with tag")
    }

    @Test
    fun d1() {
        Logcat.d("debug level string without tag")
    }

    @Test
    fun w() {
        Logcat.w("waring", "waring level string with tag")
    }

    @Test
    fun w1() {
        Logcat.w("waring level string without tag")
    }

    @Test
    fun i() {
        Logcat.i("info", "info level string with tag")
    }

    @Test
    fun i1() {
        Logcat.i("info level string without tag")
    }

    @Test
    fun e() {
        Logcat.e("error", "error level string with tag")
    }

    @Test
    fun e1() {
        Logcat.e("error level string without tag")
    }
}