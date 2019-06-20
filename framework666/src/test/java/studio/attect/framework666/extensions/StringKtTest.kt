package studio.attect.framework666.extensions

import org.junit.Test

import org.junit.Assert.*

class StringKtTest {

    @Test
    fun coverToTTSFriendlyString() {
        assert("No.1234567890 编号:1234567890 123.45￥".coverToTTSFriendlyString().equals("编号为幺,二,三,四,五,六,七,八,九,零, 编号:幺,二,三,四,五,六,七,八,九,零, 123.45元"))
    }

    @Test
    fun replaceArabicNumber2ChineseNumberForTTS() {
        assert("1234567890".replaceArabicNumber2ChineseNumberForTTS().equals("幺,二,三,四,五,六,七,八,九,零,"))
    }
}