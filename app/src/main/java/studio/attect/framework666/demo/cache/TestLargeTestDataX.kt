package studio.attect.framework666.demo.cache

import studio.attect.framework666.DataXOffice
import studio.attect.framework666.interfaces.DataX

/**
 * 大缓存测试数据
 * 会产生一个大约1GB的缓存内容
 *
 * @author Attect
 */
class TestLargeTestDataX : DataX {

    override fun putToOffice(office: DataXOffice) {
        val oneMiBString = StringBuilder(1024 * 1024).apply {
            for (i in 0 until (1024 * 1024)) {
                append("a")
            }
        }.toString()
        for (i in 0 until 1024) {
            office.putString(oneMiBString)
        }
    }

    override fun applyFromOffice(office: DataXOffice) {
        for (i in 0 until 1024) {
            office.getString()
        }
        office.getDataX(TestLargeTestDataX::class.java, Any())
    }

}