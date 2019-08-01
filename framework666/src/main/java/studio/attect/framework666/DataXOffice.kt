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

    fun putBooleanList(booleanList: List<Boolean?>?): DataXOffice {
        if (booleanList == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(booleanList.size)
            booleanList.forEach {
                putBoolean(it)
            }
        }
        return this
    }

    fun getBooleanList(): ArrayList<Boolean?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<Boolean?>()
        for (i in 0 until size) {
            arrayList.add(getBoolean())
        }
        return arrayList
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

    fun putByteList(byteList: List<Byte?>?): DataXOffice {
        if (byteList == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(byteList.size)
            byteList.forEach {
                putByte(it)
            }
        }
        return this
    }

    fun getByteList(): ArrayList<Byte?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<Byte?>()
        for (i in 0 until size) {
            arrayList.add(getByte())
        }
        return arrayList
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


    fun putIntList(intList: List<Int?>?): DataXOffice {
        if (intList == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(intList.size)
            intList.forEach {
                putInt(it)
            }
        }
        return this
    }

    fun getIntList(): ArrayList<Int?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<Int?>()
        for (i in 0 until size) {
            arrayList.add(getInt())
        }
        return arrayList
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


    fun putLongList(list: List<Long?>?): DataXOffice {
        if (list == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(list.size)
            list.forEach {
                putLong(it)
            }
        }
        return this
    }

    fun getLongList(): ArrayList<Long?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<Long?>()
        for (i in 0 until size) {
            arrayList.add(getLong())
        }
        return arrayList
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
                putBigInteger(it)
            }
        }
        return this
    }


    fun getBigIntegerArray(): Array<BigInteger?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            return@Array getBigInteger()
        }
    }

    fun putBigIntegerList(list: List<BigInteger?>?): DataXOffice {
        if (list == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(list.size)
            list.forEach {
                putBigInteger(it)
            }
        }
        return this
    }

    fun getBigIntegerList(): ArrayList<BigInteger?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<BigInteger?>()
        for (i in 0 until size) {
            arrayList.add(getBigInteger())
        }
        return arrayList
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

    fun putFloatList(list: List<Float?>?): DataXOffice {
        if (list == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(list.size)
            list.forEach {
                putFloat(it)
            }
        }
        return this
    }

    fun getFloatList(): ArrayList<Float?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<Float?>()
        for (i in 0 until size) {
            arrayList.add(getFloat())
        }
        return arrayList
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

    fun getDoubleArray(): DoubleArray? {
        if (unpacker.tryUnpackNil()) return null
        val doubleArray = DoubleArray(unpacker.unpackArrayHeader())
        for (i in 0 until doubleArray.size) {
            doubleArray[i] = unpacker.unpackDouble()
        }
        return doubleArray
    }

    fun putDoubleList(list: List<Double?>?): DataXOffice {
        if (list == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(list.size)
            list.forEach {
                putDouble(it)
            }
        }
        return this
    }

    fun getDoubleList(): ArrayList<Double?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<Double?>()
        for (i in 0 until size) {
            arrayList.add(getDouble())
        }
        return arrayList
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

    fun putStringList(list: List<String?>?): DataXOffice {
        if (list == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(list.size)
            list.forEach {
                putString(it)
            }
        }
        return this
    }

    fun getStringList(): ArrayList<String?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<String?>()
        for (i in 0 until size) {
            arrayList.add(getString())
        }
        return arrayList
    }


    fun <T, K> putMap(map: Map<T, K>?): DataXOffice {
        if (map == null) {
            packer.packNil()
            return this
        }
        packer.packMapHeader(map.size)
        return this
    }


    inline fun <reified T, reified K> getMap(keyClass: Class<T>, valueClass: Class<K>): Pair<Int, HashMap<T?, K?>>? {
        if (unpacker.tryUnpackNil()) return null
        return Pair(unpacker.unpackMapHeader(), HashMap())
    }


    fun putDataX(dataX: DataX?): DataXOffice {
        return if (dataX == null) {
            packer.packNil()
            this
        } else {
            val oldSize = packer.totalWrittenBytes
            dataX.putToOffice(this)
            if (oldSize == packer.totalWrittenBytes) throw IllegalStateException("DataX没有提交任何资料给DataX办公室，违反了规定")
            this
        }
    }

    inline fun <reified T : DataX> getDataX(clazz: Class<T>): T? {
        if (unpacker.tryUnpackNil()) return null

        val dataX = clazz.newInstance()
        dataX.applyFromOffice(this)
        return dataX
    }

    inline fun <reified T : DataX, reified K> getDataX(clazz: Class<T>, owner: K): T? {
        if (unpacker.tryUnpackNil()) return null
        val constructor = clazz.getConstructor(clazz.constructors[0].parameterTypes[0])
        val dataX = constructor.newInstance(owner)
        dataX.applyFromOffice(this)
        return dataX
    }

    fun putArray(array: Array<out DataX?>?): DataXOffice {
        if (array == null || array.isEmpty()) {
            packer.packNil()
        } else {
            packer.packArrayHeader(array.size)
            array.forEach {
                putDataX(it)
            }
        }
        return this
    }

    inline fun <reified T : DataX> getArray(cls: Class<T>): Array<T?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            return@Array getDataX(cls)
        }
    }

    inline fun <reified T : DataX, reified K> getArray(clazz: Class<T>, owner: K): Array<T?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            return@Array getDataX(clazz, owner)
        }
    }

    fun putList(list: List<DataX?>?): DataXOffice {
        if (list == null) {
            packer.packNil()
        } else {
            packer.packArrayHeader(list.size)
            list.forEach {
                putDataX(it)
            }
        }
        return this
    }

    inline fun <reified T : DataX> getList(clazz: Class<T>): ArrayList<T?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<T?>()
        for (i in 0 until size) {
            arrayList.add(getDataX(clazz))
        }
        return arrayList
    }

    inline fun <reified T : DataX, reified K> getList(clazz: Class<T>, owner: K): ArrayList<T?>? {
        if (unpacker.tryUnpackNil()) return null
        val size = unpacker.unpackArrayHeader()
        val arrayList = ArrayList<T?>()
        for (i in 0 until size) {
            arrayList.add(getDataX(clazz, owner))
        }
        return arrayList
    }

    /**
     * 积压字节数
     */
    fun backLog(): Long = packer.totalWrittenBytes

}