package studio.attect.framework666

import org.msgpack.core.MessagePack
import org.msgpack.core.MessagePacker
import org.msgpack.core.MessageUnpacker
import studio.attect.framework666.extensions.rawTypeName
import studio.attect.framework666.interfaces.DataX
import java.io.InputStream
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.math.BigInteger
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

/**
 * 缓存数据处理办公室
 * 此处将数据打包成二进制数据，并负责将其还原
 * 基于MsgPack
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

    inline fun <reified T> getArray(cls: Class<T>): Array<T?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            return@Array get(cls)
        }
    }

    inline fun <reified T, reified K> getArray(clazz: Class<T>, owner: K): Array<T?>? {
        if (unpacker.tryUnpackNil()) return null
        return Array(unpacker.unpackArrayHeader()) {
            return@Array get(clazz, owner)
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
     * 写入“任意”类型的数据实例[any]
     * 会将var定义的字段根据相关类型自动打包
     * 遇到非基本类型时，会递归调用此类将树形结构存入
     * 支持List/Map
     * 键会被丢弃
     * const/transient修饰的字段将会被跳过
     * 支持字段为null
     */
    fun put(any: Any?) {
        when {
            isWriteRawDataType(any) -> writeAuto(any)
            any == null -> packer.packNil()
            else -> {
                packer.packBoolean(false) //此对象不为null，需要在读取时将此对象实例化
                any::class.java.declaredFields.forEach { field ->
                    if (!Modifier.isFinal(field.modifiers) && !Modifier.isTransient(field.modifiers)) {
                        //                    println("put field:${field.name} : ${field.genericType.typeName} ${field.genericType}")
                        val accessible = field.isAccessible
                        if (!field.isAccessible) field.isAccessible = true
                        field.get(any).let {
                            if (simpleTypeForRead(field.genericType.rawTypeName) != null) {
                                if (it == null) {
                                    packer.packNil()
                                } else {
                                    writeAuto(it)
                                }
                            } else {
                                if (it == null) {
                                    packer.packBoolean(true) //此对象为null，而不是对象中的字段为null
                                } else {
                                    put(it)
                                }
                            }
                        }
                        field.isAccessible = accessible
                    }
                }
            }
        }
    }

    /**
     * 自动根据类型写入
     * 基本类型
     */
    private fun writeAuto(it: Any?) {
        if (it is Array<*>) { //非标准的数组也是可空的，且空带层级
            packer.packBoolean(false) //因此需要记录本身是否为空
            packer.packArrayHeader(it.size)
            it.forEach {
                put(it)
            }
            return
        }
        when (it) {
            null -> {
                packer.packNil()
            }
            is Byte -> putByte(it)
            is ByteArray -> putByteArray(it)
            is Short -> putShort(it)
            is ShortArray -> putShortArray(it)
            is Int -> putInt(it)
            is IntArray -> putIntArray(it)
            is BigInteger -> putBigInteger(it)
            is Long -> putLong(it)
            is LongArray -> putLongArray(it)
            is Boolean -> putBoolean(it)
            is BooleanArray -> putBooleanArray(it)
            is Double -> putDouble(it)
            is DoubleArray -> putDoubleArray(it)
            is Float -> putFloat(it)
            is FloatArray -> putFloatArray(it)
            is String -> putString(it)
            is DataX -> putDataX(it)
            is List<*> -> {
                packer.packArrayHeader(it.size)
                it.forEach {
                    put(it)
                }
            }
            is Map<*, *> -> {
                packer.packMapHeader(it.size)
                it.keys.forEach { key ->
                    put(key) //put key
                    put(it[key]) //put value
                }
            }
            else -> {
                error("DataXOffice: error type : ${it.javaClass.name}")
            }
        }
    }


    /**
     * 判断[any]是否可直接写入类型
     */
    private fun isWriteRawDataType(any: Any?): Boolean {
        when (any) {
            is Byte,
            is ByteArray,
            is Short,
            is ShortArray,
            is Int,
            is IntArray,
            is BigInteger,
            is Long,
            is LongArray,
            is Boolean,
            is BooleanArray,
            is Double,
            is DoubleArray,
            is Float,
            is FloatArray,
            is String,
            is DataX,
            is Array<*>,
            is List<*>,
            is Map<*, *> -> return true
        }

        return false
    }

    /**
     * 根据给定类型，从数据中还原对象
     * 由于原理是储存各类基本数据类型，一些高级类支持受限
     * [clazz]可为任意对象的class，会自动解析内部所有var字段，将数据交由MessagePack打包
     * [clazz]应该包含一个无参构造方法，或者仅含一个父类对象的构造方法（inner 内部类情况）
     * [owner]为要还原的对象的持有父类，如果[clazz]为一个内部类，需要通过[owner]提供父类对象，否则无法实例化
     * 返回根据给定的类型以及实际读取的数据所生成的对象，或者为null
     */
    fun <T> get(clazz: Class<T>, owner: Any? = null): T? {
        var isDataX = false
        clazz.interfaces.forEach {
            if (it.canonicalName == DataX::class.java.canonicalName) {
                isDataX = true
            }
        }
        val simpleType = simpleTypeForRead(clazz.canonicalName)
        if (simpleType != null) {
            if (simpleType != SIMPLE_TYPE_ARRAY) {
                val basicTypeResult = autoReadBasicType(simpleType)
                if (basicTypeResult.first) return basicTypeResult.second as T?
            } else {
                if (unpacker.unpackBoolean()) return null
                val arraySize = unpacker.unpackArrayHeader()
                val arrayInstance = java.lang.reflect.Array.newInstance(clazz.componentType, arraySize) as Array<Any?>
                for (i in 0 until arraySize) {
                    arrayInstance[i] = get(clazz.componentType, owner)
                }
                return arrayInstance as T
            }

        } else if (isDataX) { //如果是DataX，走DataX的读取逻辑
            if (unpacker.unpackBoolean()) return null
            val dataX = clazz.newInstance() as DataX
            dataX.applyFromOffice(this)
            return dataX as T
        } else {
            if (unpacker.unpackBoolean()) return null
            if (clazz.constructors.isNotEmpty()) {
                var foundConstructor = clazz.constructors[0]

                clazz.constructors.forEachIndexed { index, constructor ->
                    if (index > 0) {
                        if (foundConstructor.parameterTypes.size > constructor.parameterTypes.size) {
                            foundConstructor = constructor
                        }
                    }
                }
                var instance: Any? = null
                if (foundConstructor.parameterTypes.isNotEmpty()) {
                    if (foundConstructor.parameterTypes.size == 1 && owner != null) {
                        clazz.getDeclaredConstructor(owner::class.java).let {
                            instance = it.newInstance(owner)
                        }
                    }
                } else {
                    val tmpInstance = foundConstructor.newInstance()
                    (tmpInstance as? T).let {
                        instance = it
                    }
                }

                if (instance != null) {
                    instance?.let { target ->
                        target::class.java.declaredFields.forEach { field ->
                            if (field.name != "this\$0" && unpacker.hasNext() && !Modifier.isFinal(field.modifiers) && !Modifier.isTransient(field.modifiers)) {
//                                println("get field:${field.name} ${field.genericType.rawTypeName}")
                                if (!unpacker.tryUnpackNil()) {
                                    val accessible = field.isAccessible
                                    if (!field.isAccessible) field.isAccessible = true

                                    if (field.genericType is ParameterizedType) {
                                        val parameterizedType = field.genericType as ParameterizedType
                                        val fieldType = simpleTypeForRead(field.type.canonicalName)
                                        val fieldClass = field.type
                                        if (fieldType == SIMPLE_TYPE_LIST) {
                                            var list: List<Any?>
                                            if (fieldClass == List::class.java) {
                                                list = ArrayList()
                                            } else {
                                                list = fieldClass.newInstance() as List<Any?>
                                            }

                                            val listSize = unpacker.unpackArrayHeader()
                                            val listType = parameterizedType.actualTypeArguments[0]
                                            val listTypeName = simpleTypeForRead(listType.rawTypeName)
                                            when (listType) {
                                                is Class<*> -> {
                                                    when (list) {
                                                        is ArrayList<Any?> -> for (i in 0 until listSize) list.add(get(listType, instance))
                                                        is LinkedList<Any?> -> for (i in 0 until listSize) list.add(get(listType, instance))
                                                        else -> { //处理定义时写的是var field:List<Any?>的情况
                                                            list = ArrayList()
                                                            for (i in 0 until listSize) list.add(get(listType, instance))
                                                        }
                                                    }
                                                }
                                                is ParameterizedType -> {
                                                    when (list) {
                                                        is ArrayList<Any?> -> for (i in 0 until listSize) list.add(
                                                            autoReadParameterizedType(
                                                                instance,
                                                                listTypeName,
                                                                listType
                                                            )
                                                        )
                                                        is LinkedList<Any?> -> for (i in 0 until listSize) list.add(
                                                            autoReadParameterizedType(
                                                                instance,
                                                                listTypeName,
                                                                listType
                                                            )
                                                        )
                                                        else -> {
                                                            list = ArrayList()
                                                            for (i in 0 until listSize) list.add(autoReadParameterizedType(instance, listTypeName, listType))
                                                        }
                                                    }
                                                }
                                                else -> println("not support ParameterizedType:$parameterizedType [0]")
                                            }
                                            field.set(instance, list)
                                        } else if (fieldType == SIMPLE_TYPE_MAP) {
                                            var map = fieldClass.newInstance() as Map<Any?, Any?>
                                            val mapSize = unpacker.unpackMapHeader()
                                            val keyType = parameterizedType.actualTypeArguments[0]
                                            val keyTypeName = simpleTypeForRead(keyType.rawTypeName)
                                            val valueType = parameterizedType.actualTypeArguments[1]
                                            val valueTypeName = simpleTypeForRead(valueType.rawTypeName)

                                            var keyValue: Any? = null
                                            var valueValue: Any? = null

                                            when (map) {
                                                is HashMap,
                                                is ConcurrentHashMap,
                                                is LinkedHashMap -> {
                                                    map as MutableMap<Any?, Any?>
                                                    for (i in 0 until mapSize) {
                                                        when (keyType) {
                                                            is Class<*> -> keyValue = get(keyType, instance)
                                                            is ParameterizedType -> keyValue = autoReadParameterizedType(instance, keyTypeName, keyType)
                                                            else -> println("not support map key")
                                                        }
                                                        when (valueType) {
                                                            is Class<*> -> valueValue = get(valueType, instance)
                                                            is ParameterizedType -> valueValue = autoReadParameterizedType(instance, valueTypeName, valueType)
                                                            else -> println("not support map value")
                                                        }
                                                        map.put(keyValue, valueValue)
                                                    }
                                                }
                                                else -> {
                                                    map = HashMap()
                                                    for (i in 0 until mapSize) {
                                                        when (keyType) {
                                                            is Class<*> -> keyValue = get(keyType, instance)
                                                            is ParameterizedType -> keyValue = autoReadParameterizedType(instance, keyTypeName, keyType)
                                                            else -> println("not support map key")
                                                        }
                                                        when (valueType) {
                                                            is Class<*> -> valueValue = get(valueType, instance)
                                                            is ParameterizedType -> valueValue = autoReadParameterizedType(instance, valueTypeName, valueType)
                                                            else -> println("not support map value")
                                                        }
                                                        map.put(keyValue, valueValue)
                                                    }

                                                }
                                            }
                                            field.set(instance, map)

                                        }
                                    } else {
                                        field.set(instance, get(field.type, instance))
                                    }

                                    field.isAccessible = accessible
                                }
                            } //nil check
                        }

                    }
                }
                return instance as? T
            } else {
                return null
            }
        }
        return null
    }

    /**
     * 根据泛型类型读取数据
     * 需要提供可能的父类[instance]，读取的数据的类型[fieldSimpleType]，带泛型的类型[parameterizedType]
     * 返回读取到的数据类型，若不支持则会返回null，否则返回实例Any，类型自己强转
     */
    private fun autoReadParameterizedType(instance: Any?, fieldSimpleType: Int?, parameterizedType: ParameterizedType): Any? {
        if (fieldSimpleType == SIMPLE_TYPE_LIST) {
            var list = (parameterizedType.rawType as Class<*>).newInstance() as List<Any?>
            val listType = parameterizedType.actualTypeArguments[0]
            if (!unpacker.tryUnpackNil()) {
                val listSize = unpacker.unpackArrayHeader()
                when (listType) {
                    is Class<*> -> {
                        when (list) {
                            is ArrayList<Any?> -> for (i in 0 until listSize) list.add(get(listType, instance))
                            is LinkedList<Any?> -> for (i in 0 until listSize) list.add(get(listType, instance))
                            else -> { //处理定义时写的是var field:List<Any?>的情况
                                list = ArrayList()
                                for (i in 0 until listSize) list.add(get(listType, instance))
                            }
                        }
                    }
                    is ParameterizedType -> {
                        when (list) {
                            is ArrayList<Any?> -> for (i in 0 until listSize) list.add(autoReadParameterizedType(instance, simpleTypeForRead(listType.rawTypeName), listType))
                            is LinkedList<Any?> -> for (i in 0 until listSize) list.add(autoReadParameterizedType(instance, simpleTypeForRead(listType.rawTypeName), listType))
                            else -> {
                                list = ArrayList()
                                for (i in 0 until listSize) list.add(autoReadParameterizedType(instance, simpleTypeForRead(listType.rawTypeName), listType))
                            }
                        }
                    }
                    else -> println("not support ParameterizedType:$parameterizedType [0]") //还会有什么类型？我暂且不知道
                }
                return list
            } else {
                return null //是List 但是是null
            }


        } else if (fieldSimpleType == SIMPLE_TYPE_MAP) {
            var map = (parameterizedType.rawType as Class<*>).newInstance() as Map<Any?, Any?>
            val keyType = parameterizedType.actualTypeArguments[0]
            val keyTypeName = simpleTypeForRead(keyType.rawTypeName)
            val valueType = parameterizedType.actualTypeArguments[1]
            val valueTypeName = simpleTypeForRead(valueType.rawTypeName)

            var keyValue: Any? = null
            var valueValue: Any? = null

            if (!unpacker.tryUnpackNil()) {
                val mapSize = unpacker.unpackMapHeader()
                when (map) {
                    is HashMap,
                    is ConcurrentHashMap,
                    is LinkedHashMap -> {
                        map as MutableMap<Any?, Any?>
                        for (i in 0 until mapSize) {
                            when (keyType) {
                                is Class<*> -> keyValue = get(keyType, instance)
                                is ParameterizedType -> keyValue = autoReadParameterizedType(instance, keyTypeName, keyType)
                                else -> println("not support map key")
                            }
                            when (valueType) {
                                is Class<*> -> valueValue = get(valueType, instance)
                                is ParameterizedType -> valueValue = autoReadParameterizedType(instance, valueTypeName, valueType)
                                else -> println("not support map value")
                            }
                            map.put(keyValue, valueValue)
                        }
                    }
                    else -> {
                        map = HashMap()
                        for (i in 0 until mapSize) {
                            when (keyType) {
                                is Class<*> -> keyValue = get(keyType, instance)
                                is ParameterizedType -> keyValue = autoReadParameterizedType(instance, keyTypeName, keyType)
                                else -> println("not support map key")
                            }
                            when (valueType) {
                                is Class<*> -> valueValue = get(valueType, instance)
                                is ParameterizedType -> valueValue = autoReadParameterizedType(instance, valueTypeName, valueType)
                                else -> println("not support map value")
                            }
                            map.put(keyValue, valueValue)
                        }

                    }
                }

                return map
            } else {
                return null //是Map但是是null
            }
        }
        return null
    }


    /**
     * 将复杂的原始类型名称[typeName]归类为简单的类型名称
     * 便于后面判断
     */
    private fun simpleTypeForRead(typeName: String?): Int? {
        when (typeName) { //此处按类型出现频率排序可提高效率
            "int",
            Int::class.java.canonicalName,
            Integer::class.java.canonicalName
            -> return SIMPLE_TYPE_INT

            "boolean",
            Boolean::class.java.canonicalName,
            java.lang.Boolean::class.java.canonicalName
            -> return SIMPLE_TYPE_BOOLEAN

            String::class.java.canonicalName,
            java.lang.String::class.java.canonicalName
            -> return SIMPLE_TYPE_STRING

            "double",
            Double::class.java.canonicalName,
            java.lang.Double::class.java.canonicalName
            -> return SIMPLE_TYPE_DOUBLE

            "float",
            Float::class.java.canonicalName,
            java.lang.Float::class.java.canonicalName
            -> return SIMPLE_TYPE_FLOAT

            "long",
            Long::class.java.canonicalName,
            java.lang.Long::class.java.canonicalName
            -> return SIMPLE_TYPE_LONG

            "byte",
            Byte::class.java.canonicalName,
            java.lang.Byte::class.java.canonicalName
            -> return SIMPLE_TYPE_BYTE

            "short",
            Short::class.java.canonicalName,
            java.lang.Short::class.java.canonicalName
            -> return SIMPLE_TYPE_SHORT

            ArrayList<Any>()::class.java.canonicalName,
            LinkedList<Any>()::class.java.canonicalName,
            List::class.java.canonicalName
            -> {
                return SIMPLE_TYPE_LIST
            }

            HashMap<Any, Any>()::class.java.canonicalName,
            LinkedHashMap<Any, Any>()::class.java.canonicalName,
            ConcurrentHashMap<Any, Any>()::class.java.canonicalName,
            Map::class.java.canonicalName
            -> {
                return SIMPLE_TYPE_MAP
            }

            Array<Int>::class.java.canonicalName,
            IntArray::class.java.canonicalName
            -> return SIMPLE_TYPE_INT_ARRAY

            Array<Boolean>::class.java.canonicalName,
            BooleanArray::class.java.canonicalName
            -> return SIMPLE_TYPE_BOOLEAN_ARRAY

            Array<Double>::class.java.canonicalName,
            DoubleArray::class.java.canonicalName
            -> return SIMPLE_TYPE_DOUBLE_ARRAY

            Array<Float>::class.java.canonicalName,
            FloatArray::class.java.canonicalName
            -> return SIMPLE_TYPE_FLOAT_ARRAY

            Array<Long>::class.java.canonicalName,
            LongArray::class.java.canonicalName
            -> return SIMPLE_TYPE_LONG_ARRAY


            Array<Byte>::class.java.canonicalName,
            ByteArray::class.java.canonicalName
            -> return SIMPLE_TYPE_BYTE_ARRAY

            Array<Short>::class.java.canonicalName,
            ShortArray::class.java.canonicalName
            -> return SIMPLE_TYPE_SHORT_ARRAY

            BigInteger::class.java.canonicalName
            -> return SIMPLE_TYPE_BIG_INTEGER

            Array<BigInteger>::class.java.canonicalName
            -> return SIMPLE_TYPE_BIG_INTEGER_ARRAY

            else -> {
                if (typeName?.endsWith("[]") == true || typeName?.startsWith("[L") == true) return SIMPLE_TYPE_ARRAY
                return null
            }
        }
    }

    /**
     * 根据给定的简易类型名称[simpleType]读取数据
     * 返回成对数据，first为是否成功读取，第二个为数据，可能为null，因为存进去的可能就是个null
     */
    private fun autoReadBasicType(simpleType: Int?): Pair<Boolean, Any?> {
        simpleType?.let {
            when (it) {
                SIMPLE_TYPE_BYTE -> return Pair(true, getByte())
                SIMPLE_TYPE_BYTE_ARRAY -> return Pair(true, getByteArray())
                SIMPLE_TYPE_SHORT -> return Pair(true, getShort())
                SIMPLE_TYPE_SHORT_ARRAY -> return Pair(true, getShortArray())
                SIMPLE_TYPE_INT -> return Pair(true, getInt())
                SIMPLE_TYPE_INT_ARRAY -> return Pair(true, getIntArray())
                SIMPLE_TYPE_BIG_INTEGER -> return Pair(true, getBigInteger())
                SIMPLE_TYPE_BIG_INTEGER_ARRAY -> return Pair(true, getBigIntegerArray())
                SIMPLE_TYPE_LONG -> return Pair(true, getLong())
                SIMPLE_TYPE_LONG_ARRAY -> return Pair(true, getLongArray())
                SIMPLE_TYPE_BOOLEAN -> return Pair(true, getBoolean())
                SIMPLE_TYPE_BOOLEAN_ARRAY -> return Pair(true, getBooleanArray())
                SIMPLE_TYPE_DOUBLE -> return Pair(true, getDouble())
                SIMPLE_TYPE_DOUBLE_ARRAY -> return Pair(true, getDoubleArray())
                SIMPLE_TYPE_FLOAT -> return Pair(true, getFloat())
                SIMPLE_TYPE_FLOAT_ARRAY -> return Pair(true, getFloatArray())
                SIMPLE_TYPE_STRING -> return Pair(true, getString())
                else -> return Pair(false, null) //todo Any[]
            }
        }
        return Pair(false, null)
    }

    /**
     * 积压字节数
     */
    fun backLog(): Long = packer.totalWrittenBytes

    companion object {
        private const val SIMPLE_TYPE_BYTE = 1
        private const val SIMPLE_TYPE_BYTE_ARRAY = 2
        private const val SIMPLE_TYPE_SHORT = 3
        private const val SIMPLE_TYPE_SHORT_ARRAY = 4
        private const val SIMPLE_TYPE_INT = 5
        private const val SIMPLE_TYPE_INT_ARRAY = 6
        private const val SIMPLE_TYPE_BIG_INTEGER = 7
        private const val SIMPLE_TYPE_BIG_INTEGER_ARRAY = 8
        private const val SIMPLE_TYPE_LONG = 9
        private const val SIMPLE_TYPE_LONG_ARRAY = 10
        private const val SIMPLE_TYPE_BOOLEAN = 11
        private const val SIMPLE_TYPE_BOOLEAN_ARRAY = 12
        private const val SIMPLE_TYPE_DOUBLE = 13
        private const val SIMPLE_TYPE_DOUBLE_ARRAY = 14
        private const val SIMPLE_TYPE_FLOAT = 15
        private const val SIMPLE_TYPE_FLOAT_ARRAY = 16
        private const val SIMPLE_TYPE_STRING = 17
        private const val SIMPLE_TYPE_LIST = 18
        private const val SIMPLE_TYPE_MAP = 19
        private const val SIMPLE_TYPE_ARRAY = 20
        private const val SIMPLE_TYPE_ANY = 21

    }
}