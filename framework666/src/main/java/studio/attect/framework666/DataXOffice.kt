package studio.attect.framework666

import org.msgpack.core.MessagePack
import org.msgpack.core.MessagePacker
import org.msgpack.core.MessageUnpacker
import studio.attect.framework666.interfaces.DataX
import java.io.InputStream
import java.math.BigInteger

/**
 * 缓存数据处理办公室
 * @author Attect
 */
class DataXOffice(private val packer: MessagePacker = MessagePack.newDefaultBufferPacker()) {
    lateinit var unpacker: MessageUnpacker

    fun unpack(byteArray: ByteArray): DataXOffice {
        unpacker = MessagePack.newDefaultUnpacker(byteArray)
        return this
    }

    fun unpack(inputStream: InputStream): DataXOffice {
        unpacker = MessagePack.newDefaultUnpacker(inputStream)
        return this
    }

    fun putBoolean(boolean: Boolean?): DataXOffice {
        if (boolean == null) {
            packer.packNil()
        } else {
            packer.packBoolean(boolean)
        }
        return this
    }

    fun getBoolean(): Boolean? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackBoolean()
    }

    fun putBooleanArray(booleanArray: BooleanArray?): DataXOffice {
        if (booleanArray == null || booleanArray.isEmpty()) { //MessagePack数组长度不能为0
            packer.packNil()
        } else {
            packer.packArrayHeader(booleanArray.size)
            booleanArray.forEach { packer.packBoolean(it) }
        }
        return this
    }

    fun getBooleanArray(): BooleanArray? {
        if (unpacker.tryUnpackNil()) return null
        val booleanArray = BooleanArray(unpacker.unpackArrayHeader())
        for (i in 0 until booleanArray.size) {
            booleanArray[i] = unpacker.unpackBoolean()
        }
        return booleanArray
    }

    fun putByte(byte: Byte?): DataXOffice {
        if (byte == null) {
            packer.packNil()
        } else {
            packer.packByte(byte)
        }
        return this
    }

    fun getByte(): Byte? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackByte()
    }

    fun putByteArray(byteArray: ByteArray?): DataXOffice {
        if (byteArray == null || byteArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(byteArray.size)
            byteArray.forEach { packer.packByte(it) }
        }
        return this
    }

    fun getByteArray(): ByteArray? {
        if (unpacker.tryUnpackNil()) return null
        val byteArray = ByteArray(unpacker.unpackArrayHeader())
        for (i in 0 until byteArray.size) {
            byteArray[i] = unpacker.unpackByte()
        }
        return byteArray
    }

    fun putShort(short: Short?): DataXOffice {
        if (short == null) {
            packer.packNil()
        } else {
            packer.packShort(short)
        }
        return this
    }

    fun getShort(): Short? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackShort()
    }

    fun putShortArray(shortArray: ShortArray?): DataXOffice {
        if (shortArray == null || shortArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(shortArray.size)
            shortArray.forEach { packer.packShort(it) }
        }
        return this
    }

    fun getShortArray(): ShortArray? {
        if (unpacker.tryUnpackNil()) return null
        val shortArray = ShortArray(unpacker.unpackArrayHeader())
        for (i in 0 until shortArray.size) {
            shortArray[i] = unpacker.unpackShort()
        }
        return shortArray
    }

    fun putInt(int: Int?): DataXOffice {
        if (int == null) {
            packer.packNil()
        } else {
            packer.packInt(int)
        }
        return this
    }

    fun getInt(): Int? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackInt()
    }

    fun putIntArray(intArray: IntArray?): DataXOffice {
        if (intArray == null || intArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(intArray.size)
            intArray.forEach { packer.packInt(it) }
        }
        return this
    }

    fun getIntArray(): IntArray? {
        if (unpacker.tryUnpackNil()) return null
        val intArray = IntArray(unpacker.unpackArrayHeader())
        for (i in 0 until intArray.size) {
            intArray[i] = unpacker.unpackInt()
        }
        return intArray
    }

    fun putLong(long: Long?): DataXOffice {
        if (long == null) {
            packer.packNil()
        } else {
            packer.packLong(long)
        }
        return this
    }

    fun getLong(): Long? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackLong()
    }

    fun putLongArray(longArray: LongArray?): DataXOffice {
        if (longArray == null || longArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(longArray.size)
            longArray.forEach { packer.packLong(it) }
        }
        return this
    }

    fun getLongArray(): LongArray? {
        if (unpacker.tryUnpackNil()) return null
        val longArray = LongArray(unpacker.unpackArrayHeader())
        for (i in 0 until longArray.size) {
            longArray[i] = unpacker.unpackLong()
        }
        return longArray
    }

    fun putBigInteger(bigInteger: BigInteger?): DataXOffice {
        if (bigInteger == null) {
            packer.packNil()
        } else {
            packer.packBigInteger(bigInteger)
        }
        return this
    }

    fun getBigInteger(): BigInteger? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackBigInteger()
    }


    fun putBigIntegerArray(array: Array<BigInteger?>?): DataXOffice {
        if (array == null || array.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(array.size)
            array.forEach {
                if (it == null) {
                    packer.packNil()
                } else {
                    packer.packBigInteger(it)
                }
            }
        }
        return this
    }


    fun getBigIntegerArray(): Array<BigInteger?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            if (unpacker.tryUnpackNil()) return@Array null
            return@Array unpacker.unpackBigInteger()
        }
    }

    fun putFloat(float: Float?): DataXOffice {
        if (float == null) {
            packer.packNil()
        } else {
            packer.packFloat(float)
        }
        return this
    }

    fun getFloat(): Float? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackFloat()
    }

    fun putFloatArray(floatArray: FloatArray?): DataXOffice {
        if (floatArray == null || floatArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(floatArray.size)
            floatArray.forEach { packer.packFloat(it) }
        }
        return this
    }

    fun getFloatArray(): FloatArray? {
        if (unpacker.tryUnpackNil()) return null
        val floatArray = FloatArray(unpacker.unpackArrayHeader())
        for (i in 0 until floatArray.size) {
            floatArray[i] = unpacker.unpackFloat()
        }
        return floatArray
    }

    fun putDouble(double: Double?): DataXOffice {
        if (double == null) {
            packer.packNil()
        } else {
            packer.packDouble(double)
        }
        return this
    }

    fun getDouble(): Double? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackDouble()
    }

    fun putDoubleArray(doubleArray: DoubleArray?): DataXOffice {
        if (doubleArray == null || doubleArray.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(doubleArray.size)
            doubleArray.forEach { packer.packDouble(it) }
        }
        return this
    }

    fun putString(string: String?): DataXOffice {
        if (string == null) {
            packer.packNil()
        } else {
            packer.packString(string)
        }
        return this
    }

    fun getString(): String? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackString()
    }

    fun putStringArray(array: Array<String?>?): DataXOffice {
        if (array == null || array.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(array.size)
            array.forEach {
                if (it == null) {
                    packer.packNil()
                } else {
                    packer.packString(it)
                }
            }
        }
        return this
    }

    fun getStringArray(): Array<String?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            if (unpacker.tryUnpackNil()) return@Array null
            return@Array unpacker.unpackString()
        }
    }

    fun putArray(array: Array<out DataX?>?): DataXOffice {
        if (array == null || array.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(array.size)
            array.forEach {
                if (it == null) {
                    packer.packNil()
                } else {
                    val oldSize = packer.totalWrittenBytes
                    it.putToOffice(this)
                    if (oldSize == packer.totalWrittenBytes) {
                        throw IllegalStateException("数组中的对象没有给DataX办公室提交任何东西，请确保至少送达一个数据")
                    }
                }
            }
        }
        return this
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

    fun putDataX(dataX: DataX): DataXOffice {
        val oldSize = packer.totalWrittenBytes
        dataX.putToOffice(this)
        if (oldSize == packer.totalWrittenBytes) throw IllegalStateException("DataX没有提交任何资料给DataX办公室，违反了规定")
        return this
    }

    fun putMap(mapSize: Int): DataXOffice {
        packer.packMapHeader(mapSize)
        return this
    }

    fun getMap(): Int? {
        if (unpacker.tryUnpackNil()) return null
        return unpacker.unpackMapHeader()
    }

    /**
     * 积压字节数
     */
    fun backLog(): Long = packer.totalWrittenBytes

}