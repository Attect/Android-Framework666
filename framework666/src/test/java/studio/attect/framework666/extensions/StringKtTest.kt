package studio.attect.framework666.extensions

import org.junit.Test

class StringKtTest {


    @Test
    fun replaceArabicNumber2ChineseNumberForTTS() {
        assert("1234567890".replaceArabicNumber2ChineseNumberForTTS().equals("幺,二,三,四,五,六,七,八,九,零,"))
    }
}