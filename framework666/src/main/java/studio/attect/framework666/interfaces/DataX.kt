package studio.attect.framework666.interfaces

import studio.attect.framework666.DataXOffice

/**
 * 能被缓存数据处理办公室处理的档案X
 *
 * @author Attect
 */
interface DataX {
    /**
     * DataX办公室向此对象所要数据
     * 在此方法将数据塞进DataX办公室
     */
    fun putToOffice(office: DataXOffice)

    /**
     * 从DataX办公室拿取自己对应的数据
     * 不可以多拿！
     * 也不可以少拿！
     */
    fun applyFromOffice(office: DataXOffice)
}