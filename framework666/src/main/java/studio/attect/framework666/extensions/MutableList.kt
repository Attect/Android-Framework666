package studio.attect.framework666.extensions

/**
 * 将可变列表中的一项移动到指定位置
 */
fun <E> MutableList<E>.move(from: Int, to: Int) {
    if (from == to) return
    val targetData = get(from)
    if (from > to) {
        add(to, targetData)
        removeAt(from + 1)
    } else {
        add(to + 1, targetData)
        removeAt(from)
    }
}