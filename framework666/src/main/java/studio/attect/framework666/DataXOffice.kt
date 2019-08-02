package studio.attect.framework666

import org.msgpack.core.MessagePack
import org.msgpack.core.MessagePacker
import org.msgpack.core.MessageUnpacker
import studio.attect.framework666.extensions.rawTypeName
import studio.attect.framework666.interfaces.DataX
import java.io.InputStream
import java.lang.reflect.ParameterizedType
import java.math.BigInteger
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaType

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
     * 写入“任意”类型的数据实例[any]
     * 会将var定义的字段根据相关类型自动打包
     * 遇到非基本类型时，会递归调用此类将树形结构存入
     * 支持List/Map
     * 键会被丢弃
     * const/final修饰的字段将会被跳过
     * 支持字段为null
     */
    fun put(any: Any?) {
        if (any == null) {
            packer.packNil()
            return
        }
        if (isWriteRawDataType(any)) {
            writeAuto(any)
        } else {
            any::class::memberProperties.get().forEach { kField ->
                if (kField is KMutableProperty<*> && !kField.isConst && !kField.isFinal) { //跳过val类型、const/final关键字
                    val accessible = kField.isAccessible
                    if (!kField.isAccessible) kField.isAccessible = true
                    val nullable = kField.returnType.isMarkedNullable
                    kField.getter.call(any).let { field ->
                        if (field != null && isWriteRawDataType(field)) {
                            writeAuto(field)
                        } else {
                            put(field)
                        }
                    }

                    kField.isAccessible = accessible
                }

            }
        }
    }

    /**
     * 自动根据类型写入
     * 基本类型
     */
    private fun writeAuto(it: Any?) {
        when (it) {
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

            is Array<*> -> {
                if (it.isNullOrEmpty()) {
                    packer.packNil()
                } else {
                    packer.packArrayHeader(it.size)
                    it.forEach {
                        put(it)
                    }
                }
            }

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

            null -> {
                packer.packNil()
            }

            else -> {
                error("DataXOffice: error type : ${it.javaClass.name}")
            }
        }
    }


    /**
     * 判断[any]是否可直接写入类型
     */
    private fun isWriteRawDataType(any: Any): Boolean {
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
            is Array<*>,
            is List<*>,
            is Map<*, *> -> return true
        }

        return false
    }

    fun <T> get(clazz: Class<T>, owner: Any? = null): T? {
        val simpleTypeName = simpleTypeForRead(clazz.canonicalName)
        if (simpleTypeName != null) {
            val basicTypeResult = autoReadBasicType(simpleTypeName)
            if (basicTypeResult.first) return basicTypeResult.second as T?
        } else {
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
                        println("try create inner class instance")
                        clazz.getDeclaredConstructor(owner::class.java).let {
                            println("inner class instance success")
                            instance = it.newInstance(owner)
                        }
                    } else {
                        println("unable create ${clazz.name} instance")
                        println("parameterTypes.size:${foundConstructor.parameterTypes.size}")
                        foundConstructor.parameterTypes.forEach {
                            println(it.canonicalName)
                        }
                        //todo data class
                    }
                } else {
                    val tmpInstance = foundConstructor.newInstance()
                    (tmpInstance as? T).let {
                        instance = it
                    }

                }

                if (instance != null) {
                    instance?.let { target ->
                        println("object:" + target::class.java.canonicalName)
                        target::class::memberProperties.get().forEach { kField ->
                            val accessible = kField.isAccessible
                            if (!kField.isAccessible) kField.isAccessible = true
                            val nullable = kField.returnType.isMarkedNullable
                            print("field: ")
                            print(kField.returnType.javaType.toString())
                            if (nullable) print("?")
                            println(" " + kField.name)
                            if (kField.returnType.javaType is ParameterizedType) {
                                val parameterizedType = kField.returnType.javaType as ParameterizedType
                                kField.javaField?.let { javaField ->
                                    println("${kField.name}:${simpleTypeForRead(javaField.type.canonicalName)} has ParameterizedType:${kField.returnType.javaType}")
                                    val fieldTypeName = simpleTypeForRead(javaField.type.canonicalName)
                                    if (fieldTypeName == "List") {
                                        println("List java field:${kField.javaField?.genericType}")
                                        if (!unpacker.tryUnpackNil()) { //List对象为null
                                            kField.javaField?.type?.let { fieldClass ->
                                                var list = fieldClass.newInstance() as List<Any?>
                                                val listSize = unpacker.unpackArrayHeader()
                                                val listType = parameterizedType.actualTypeArguments[0]
                                                val listTypeName = simpleTypeForRead(listType.rawTypeName)
                                                println("simpleListTypeName:$listTypeName rawName:${listType.rawTypeName} name:${listType}")
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
                                                                    simpleTypeForRead(listType.rawTypeName),
                                                                    listType
                                                                )
                                                            )
                                                            is LinkedList<Any?> -> for (i in 0 until listSize) list.add(
                                                                autoReadParameterizedType(
                                                                    instance,
                                                                    simpleTypeForRead(listType.rawTypeName),
                                                                    listType
                                                                )
                                                            )
                                                            else -> {
                                                                list = ArrayList()
                                                                for (i in 0 until listSize) list.add(autoReadParameterizedType(instance, simpleTypeForRead(listType.rawTypeName), listType))
                                                            }
                                                        }
                                                    }
                                                    else -> println("not support ParameterizedType:$parameterizedType [0]")
                                                }
                                                if (kField is KMutableProperty<*>) {
                                                    kField.setter.call(instance, list)
                                                }
                                            }
                                        }

                                    } else if (fieldTypeName == "Map") {
                                        println("Map java field:${kField.javaField?.genericType}")
                                        if (!unpacker.tryUnpackNil()) { //Map对象为null
                                            kField.javaField?.type?.let { fieldClass ->
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

                                                if (kField is KMutableProperty<*>) {
                                                    kField.setter.call(instance, map)
                                                }

                                            }

                                        }
                                    }
                                }

                            } else {
                                if (kField is KMutableProperty<*>) {
                                    val fieldTypeClass = kField.javaField?.type
                                    if (fieldTypeClass != null) {
                                        kField.setter.call(instance, get(fieldTypeClass, instance))
                                    } else {
                                        println("todo field class null")
                                        //todo field class null
                                    }
                                }
                            }
                        }

                    }
                }

                println("""instance :${instance.toString()}""")
                return instance as? T
            } else {
                println("null because of no constructor")
                return null
            }
        }
        println("null because of rule bug")
        return null
    }

    private fun autoReadParameterizedType(instance: Any?, fieldTypeName: String?, parameterizedType: ParameterizedType): Any? {
        if (fieldTypeName == "List") {
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
                    else -> println("not support ParameterizedType:$parameterizedType [0]")
                }
                return list
            } else {
                return null //是List 但是是null
            }


        } else if (fieldTypeName == "Map") {
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
        println("not support ParameterizedType:$parameterizedType [1]")
        return null
    }


    private fun simpleTypeForRead(typeName: String?): String? {
        when (typeName) {
            "byte",
            Byte::class.java.canonicalName,
            java.lang.Byte::class.java.canonicalName
            -> return "byte"

            Array<Byte>::class.java.canonicalName,
            ByteArray::class.java.canonicalName
            -> return "ByteArray"

            "short",
            Short::class.java.canonicalName,
            java.lang.Short::class.java.canonicalName
            -> return "short"

            Array<Short>::class.java.canonicalName,
            ShortArray::class.java.canonicalName
            -> return "ShortArray"

            "int",
            Int::class.java.canonicalName,
            Integer::class.java.canonicalName
            -> return "int"

            Array<Int>::class.java.canonicalName,
            IntArray::class.java.canonicalName
            -> return "IntArray"

            BigInteger::class.java.canonicalName
            -> return "BigInteger"

            Array<BigInteger>::class.java.canonicalName
            -> return "BigIntegerArray"

            "long",
            Long::class.java.canonicalName,
            java.lang.Long::class.java.canonicalName
            -> return "long"

            Array<Long>::class.java.canonicalName,
            LongArray::class.java.canonicalName
            -> return "LongArray"

            "boolean",
            Boolean::class.java.canonicalName,
            java.lang.Boolean::class.java.canonicalName
            -> return "boolean"

            Array<Boolean>::class.java.canonicalName,
            BooleanArray::class.java.canonicalName
            -> return "BooleanArray"

            "double",
            Double::class.java.canonicalName,
            java.lang.Double::class.java.canonicalName
            -> return "double"

            Array<Double>::class.java.canonicalName,
            DoubleArray::class.java.canonicalName
            -> return "DoubleArray"

            "float",
            Float::class.java.canonicalName,
            java.lang.Float::class.java.canonicalName
            -> return "float"

            Array<Float>::class.java.canonicalName,
            FloatArray::class.java.canonicalName
            -> return "FloatArray"

            String::class.java.canonicalName,
            java.lang.String::class.java.canonicalName
            -> return "String"

            Array<String>::class.java.canonicalName
            -> return "StringArray"

            ArrayList<Any>()::class.java.canonicalName,
            LinkedList<Any>()::class.java.canonicalName,
            List::class.java.canonicalName
            -> {
                return "List"
            }

            HashMap<Any, Any>()::class.java.canonicalName,
            LinkedHashMap<Any, Any>()::class.java.canonicalName,
            ConcurrentHashMap<Any, Any>()::class.java.canonicalName,
            Map::class.java.canonicalName
            -> {
                return "Map"
            }

            else -> return null
        }
    }

    private fun autoReadBasicType(simpleTypeName: String?): Pair<Boolean, Any?> {
        simpleTypeName?.let {
            when (it) {
                "byte" -> return Pair(true, getByte())
                "ByteArray" -> return Pair(true, getByteArray())
                "short" -> return Pair(true, getShort())
                "ShortArray" -> return Pair(true, getShortArray())
                "int" -> return Pair(true, getInt())
                "IntArray" -> return Pair(true, getIntArray())
                "BigInteger" -> return Pair(true, getBigInteger())
                "long" -> return Pair(true, getLong())
                "LongArray" -> return Pair(true, getLongArray())
                "boolean" -> return Pair(true, getBoolean())
                "BooleanArray" -> return Pair(true, getBooleanArray())
                "double" -> return Pair(true, getDouble())
                "DoubleArray" -> return Pair(true, getDoubleArray())
                "float" -> return Pair(true, getFloat())
                "FloatArray" -> return Pair(true, getFloatArray())
                "String" -> return Pair(true, getString())
                else -> return Pair(false, null)
            }
        }
        return Pair(false, null)
    }

    /**
     * 积压字节数
     */
    fun backLog(): Long = packer.totalWrittenBytes

}