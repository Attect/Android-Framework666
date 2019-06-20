package studio.attect.framework666.extensions

/**
 * 此文件针对Java/Kotlin的String进行方法扩展
 */

/**
 * 将字符串转换为针对TTS发音友好的内容
 * 转换后的内容不适合作为显示内容
 */
fun String.coverToTTSFriendlyString(): String {
    var result = this
    val noRegex = Regex("No.[0-9]+")
    result = noRegex.replace(result) {
        it.value.replaceArabicNumber2ChineseNumberForTTS()
    }
    val haoRegex = Regex("号:[0-9]+")
    result = haoRegex.replace(result) {
        it.value.replaceArabicNumber2ChineseNumberForTTS()
    }
    result = result.replace("No.", "编号为")
    result = result.replace("￥", "元")
    return result
}

/**
 * 将字符串中应该单个念出的数字转换格式使其一个一个数字的读出
 * 使用一个逗号来强制TTS引擎在每个数字后停顿
 * 一使用yao发音防止部分TTS引擎一七难分
 */
fun String.replaceArabicNumber2ChineseNumberForTTS():String{
    var resultString = this
    resultString = resultString.replace("0", "零,")
    resultString = resultString.replace("1", "幺,")//避免与七接近
    resultString = resultString.replace("2", "二,")
    resultString = resultString.replace("3", "三,")
    resultString = resultString.replace("4", "四,")
    resultString = resultString.replace("5", "五,")
    resultString = resultString.replace("6", "六,")
    resultString = resultString.replace("7", "七,")
    resultString = resultString.replace("8", "八,")
    resultString = resultString.replace("9", "九,")
    return resultString
}