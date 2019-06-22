package studio.attect.framework666.extensions

import org.junit.Test

import org.junit.Assert.*

class LongKtTest {

    @Test
    fun toDataSizeString() {
        assert(20L.toDataSizeString() == "20Byte")
        assert(1024L.toDataSizeString() == "1.00KiB")
        assert((1024L * 1024L).toDataSizeString() == "1.00MiB")
        assert((1024L * 1024L * 1024).toDataSizeString() == "1.00GiB")
        assert((1024L * 1024L * 1024 * 1024).toDataSizeString() == "1.00TiB")
        assert((1024L * 1024L * 1024 * 1024 * 1024).toDataSizeString() == "1.00PiB")
        assert((1024L * 1024L * 1024 * 1024 * 1024 * 1024).toDataSizeString() == "1.00EiB")
    }
}