package studio.attect.framework666

import org.msgpack.core.MessagePack
import org.msgpack.core.MessageUnpacker
import studio.attect.framework666.interfaces.DataX
import java.math.BigInteger

/**
 * 缓存数据处理办公室
 * @author Attect
 */
class DataXOffice {
    val packer = MessagePack.newDefaultBufferPacker()
    lateinit var unpacker: MessageUnpacker

    fun putBoolean(boolean: Boolean?) {
        if (boolean == null) {
            packer.packNil()
        } else {
            packer.packBoolean(boolean)
        }
    }

    fun getBoolean(): Boolean? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackBoolean()
    }

    fun putBooleanArray(booleanArray: BooleanArray?) {
        if (booleanArray == null || booleanArray.isEmpty()) { //MessagePack数组长度不能为0
            packer.packNil()
        } else {
            packer.packArrayHeader(booleanArray.size)
            booleanArray.forEach { packer.packBoolean(it) }
        }
    }

    fun getBooleanArray(): BooleanArray? {
        if (unpacker.tryUnpackNil()) return null
        val booleanArray = BooleanArray(unpacker.unpackArrayHeader())
        for (i in 0 until booleanArray.size) {
            booleanArray[i] = unpacker.unpackBoolean()
        }
        return booleanArray
    }

    fun putByte(byte: Byte?) {
        if (byte == null) {
            packer.packNil()
        } else {
            packer.packByte(byte)
        }
    }

    fun getByte(): Byte? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackByte()
    }

    fun putByteArray(byteArray: ByteArray?) {
        if (byteArray == null || byteArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(byteArray.size)
            byteArray.forEach { packer.packByte(it) }
        }
    }

    fun getByteArray(): ByteArray? {
        if (unpacker.tryUnpackNil()) return null
        val byteArray = ByteArray(unpacker.unpackArrayHeader())
        for (i in 0 until byteArray.size) {
            byteArray[i] = unpacker.unpackByte()
        }
        return byteArray
    }

    fun putShort(short: Short?) {
        if (short == null) {
            packer.packNil()
        } else {
            packer.packShort(short)
        }
    }

    fun getShort(): Short? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackShort()
    }

    fun putShortArray(shortArray: ShortArray?) {
        if (shortArray == null || shortArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(shortArray.size)
            shortArray.forEach { packer.packShort(it) }
        }
    }

    fun getShortArray(): ShortArray? {
        if (unpacker.tryUnpackNil()) return null
        val shortArray = ShortArray(unpacker.unpackArrayHeader())
        for (i in 0 until shortArray.size) {
            shortArray[i] = unpacker.unpackShort()
        }
        return shortArray
    }

    fun putInt(int: Int?) {
        if (int == null) {
            packer.packNil()
        } else {
            packer.packInt(int)
        }
    }

    fun getInt(): Int? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackInt()
    }

    fun putIntArray(intArray: IntArray?) {
        if (intArray == null || intArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(intArray.size)
            intArray.forEach { packer.packInt(it) }
        }
    }

    fun getIntArray(): IntArray? {
        if (unpacker.tryUnpackNil()) return null
        val intArray = IntArray(unpacker.unpackArrayHeader())
        for (i in 0 until intArray.size) {
            intArray[i] = unpacker.unpackInt()
        }
        return intArray
    }

    fun putLong(long: Long?) {
        if (long == null) {
            packer.packNil()
        } else {
            packer.packLong(long)
        }
    }

    fun getLong(): Long? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackLong()
    }

    fun putLongArray(longArray: LongArray?) {
        if (longArray == null || longArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(longArray.size)
            longArray.forEach { packer.packLong(it) }
        }
    }

    fun getLongArray(): LongArray? {
        if (unpacker.tryUnpackNil()) return null
        val longArray = LongArray(unpacker.unpackArrayHeader())
        for (i in 0 until longArray.size) {
            longArray[i] = unpacker.unpackLong()
        }
        return longArray
    }

    fun putBigInteger(bigInteger: BigInteger?) {
        if (bigInteger == null) {
            packer.packNil()
        } else {
            packer.packBigInteger(bigInteger)
        }
    }

    fun getBigInteger(): BigInteger? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackBigInteger()
    }

    fun getBigIntegerArray(): Array<BigInteger?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            if (unpacker.tryUnpackNil()) return@Array null
            return@Array unpacker.unpackBigInteger()
        }
    }

    fun putFloat(float: Float?) {
        if (float == null) {
            packer.packNil()
        } else {
            packer.packFloat(float)
        }
    }

    fun getFloat(): Float? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackFloat()
    }

    fun putFloatArray(floatArray: FloatArray?) {
        if (floatArray == null || floatArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(floatArray.size)
            floatArray.forEach { packer.packFloat(it) }
        }
    }

    fun getFloatArray(): FloatArray? {
        if (unpacker.tryUnpackNil()) return null
        val floatArray = FloatArray(unpacker.unpackArrayHeader())
        for (i in 0 until floatArray.size) {
            floatArray[i] = unpacker.unpackFloat()
        }
        return floatArray
    }

    fun putDouble(double: Double?) {
        if (double == null) {
            packer.packNil()
        } else {
            packer.packDouble(double)
        }
    }

    fun getDouble(): Double? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackDouble()
    }

    fun putDoubleArray(doubleArray: DoubleArray?) {
        if (doubleArray == null || doubleArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(doubleArray.size)
            doubleArray.forEach { packer.packDouble(it) }
        }
    }

    fun putString(string: String?) {
        if (string == null) {
            packer.packNil()
        } else {
            packer.packString(string)
        }
    }

    fun getString(): String? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackString()
    }

    fun getStringArray(): Array<String?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            if (unpacker.tryUnpackNil()) return@Array null
            return@Array unpacker.unpackString()
        }
    }

    fun <T> putArray(array: Array<T?>?) {
        if (array == null || array.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(array.size)
            array.forEach {
                if (it == null) {
                    packer.packNil()
                } else if (it is BigInteger) {
                    packer.packBigInteger(it)
                } else if (it is String) {
                    packer.packString(it)
                } else if (it is DataX) {
                    val oldSize = packer.totalWrittenBytes
                    it.putToOffice(this)
                    if (oldSize == packer.totalWrittenBytes) {
                        throw IllegalStateException("数组中的对象没有给DataX办公室提交任何东西，请确保至少送达一个数据")
                    }
                } else {
                    throw IllegalStateException("数组中的类型无法被DataX办公室受理，请确保数据是DataX类型的")
                }
            }
        }
    }

    inline fun <reified T : DataX> getArray(cls: Class<T>): Array<T?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            if (unpacker.tryUnpackNil()) return@Array null
            val data = cls.newInstance()
            data.applyFromOffice(this)
            return@Array data
        }
    }


}