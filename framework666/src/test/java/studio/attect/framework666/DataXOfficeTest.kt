package studio.attect.framework666

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import org.msgpack.core.MessagePack
import studio.attect.framework666.extensions.toHexString
import studio.attect.framework666.interfaces.DataX

class DataXOfficeTest {

    @Test
    fun putBoolean() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        dataXOffice.putBoolean(true)
        dataXOffice.putBoolean(false)
        dataXOffice.putBoolean(null)
        assert(packer.toByteArray().toHexString() == "C3 C2 C0")
    }

    @Test
    fun getBoolean() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        dataXOffice.putBoolean(true)
        dataXOffice.putBoolean(false)
        dataXOffice.putBoolean(null)
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        assert(dataXOffice.getBoolean() == true)
        assert(dataXOffice.getBoolean() == false)
        assert(dataXOffice.getBoolean() == null)
    }

    @Test
    fun putBooleanArray() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        dataXOffice.putBooleanArray(booleanArrayOf(true, false, true))
        assert(packer.toByteArray().toHexString() == "93 C3 C2 C3")
    }

    @Test
    fun getBooleanArray() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        dataXOffice.putBooleanArray(booleanArrayOf(true, false, true))
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        assertArrayEquals(dataXOffice.getBooleanArray(), booleanArrayOf(true, false, true))
    }

    @Test
    fun putByte() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        dataXOffice.putByte(0b00000001)
        dataXOffice.putByte(0b00000010)
        dataXOffice.putByte(0b00000011)
        assert(packer.toByteArray().toHexString() == "01 02 03")
    }

    @Test
    fun getByte() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        dataXOffice.putByte(0b00000001)
        dataXOffice.putByte(0b00000010)
        dataXOffice.putByte(0b00000011)
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        val byte1 = 0b00000001.toByte()
        val byte2 = 0b00000010.toByte()
        val byte3 = 0b00000011.toByte()
        assert(dataXOffice.getByte() == byte1)
        assert(dataXOffice.getByte() == byte2)
        assert(dataXOffice.getByte() == byte3)
    }

    @Test
    fun putByteArray() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        dataXOffice.putByteArray(byteArrayOf(0b00000001, 0b00000010, 0b00000011))
        assert(packer.toByteArray().toHexString() == "93 01 02 03")
    }

    @Test
    fun getByteArray() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        dataXOffice.putByteArray(byteArrayOf(0b00000001, 0b00000010, 0b00000011))
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        assertArrayEquals(dataXOffice.getByteArray(), byteArrayOf(0b00000001, 0b00000010, 0b00000011))
    }

    @Test
    fun putShort() {
    }

    @Test
    fun getShort() {
    }

    @Test
    fun putShortArray() {
    }

    @Test
    fun getShortArray() {
    }

    @Test
    fun putInt() {
    }

    @Test
    fun getInt() {
    }

    @Test
    fun putIntArray() {
    }

    @Test
    fun getIntArray() {
    }

    @Test
    fun putLong() {
    }

    @Test
    fun getLong() {
    }

    @Test
    fun putLongArray() {
    }

    @Test
    fun getLongArray() {
    }

    @Test
    fun putBigInteger() {
    }

    @Test
    fun getBigInteger() {
    }

    @Test
    fun putBigIntegerArray() {
    }

    @Test
    fun getBigIntegerArray() {
    }

    @Test
    fun putFloat() {
    }

    @Test
    fun getFloat() {
    }

    @Test
    fun putFloatArray() {
    }

    @Test
    fun getFloatArray() {
    }

    @Test
    fun putDouble() {
    }

    @Test
    fun getDouble() {
    }

    @Test
    fun putDoubleArray() {
    }

    @Test
    fun putString() {
    }

    @Test
    fun getString() {
    }

    @Test
    fun putStringArray() {
    }

    @Test
    fun getStringArray() {
    }

    @Test
    fun putArray() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val userJack = UserData(900001, "jack")
        val userTom = UserData(900002, "tom")
        val userJerry = UserData(900003, "jerry")
        dataXOffice.putArray(arrayOf(userJack, userTom, userJerry))
        assert(packer.toByteArray().toHexString() == "93 CE 00 0D BB A1 A4 6A 61 63 6B CE 00 0D BB A2 A3 74 6F 6D CE 00 0D BB A3 A5 6A 65 72 72 79")
    }

    @Test
    fun getArray() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val userJack = UserData(900001, "jack")
        val userTom = UserData(900002, "tom")
        val userJerry = UserData(900003, "jerry")
        dataXOffice.putArray(arrayOf(userJack, userTom, userJerry))
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        val userArray = dataXOffice.getArray(UserData::class.java)
        assert(userArray?.get(0) == userJack)
        assert(userArray?.get(1) == userTom)
        assert(userArray?.get(2) == userJerry)
    }

    @Test
    fun putDataX() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val userData = UserData(6385678, "Attect")
        dataXOffice.putDataX(userData)
        println(packer.toByteArray().toHexString())
    }

    @Test
    fun getDataX() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val userData = UserData(6385678, "Attect")
        dataXOffice.putDataX(userData)
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        val restoreUserData = UserData().apply { applyFromOffice(dataXOffice) }
        assert(userData == restoreUserData)
    }

    @Test
    fun putMap() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val priceMap = HashMap<String, Double>()
        priceMap.put("OnePlus5", 2999.0)
        priceMap.put("Huawei P30", 3988.0)
        priceMap.put("Redmi", 799.0)

        dataXOffice.putMap(priceMap.size)
        priceMap.forEach { (phone, price) ->
            dataXOffice.putString(phone)
            dataXOffice.putDouble(price)
        }

        assert(packer.toByteArray().toHexString() == "83 AA 48 75 61 77 65 69 20 50 33 30 CB 40 AF 28 00 00 00 00 00 A8 4F 6E 65 50 6C 75 73 35 CB 40 A7 6E 00 00 00 00 00 A5 52 65 64 6D 69 CB 40 88 F8 00 00 00 00 00")
    }

    @Test
    fun getMap() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val priceMap = HashMap<String, Double>()
        priceMap.put("OnePlus5", 2999.0)
        priceMap.put("Huawei P30", 3988.0)
        priceMap.put("Redmi", 799.0)

        dataXOffice.putMap(priceMap.size)
        priceMap.forEach { (phone, price) ->
            dataXOffice.putString(phone)
            dataXOffice.putDouble(price)
        }

        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        val restorePriceMap = HashMap<String, Double>()
        val mapSize = dataXOffice.getMap()
        mapSize?.let {
            for (i in 0 until it) {
                dataXOffice.getString()?.let { key ->
                    dataXOffice.getDouble()?.let { value ->
                        restorePriceMap.put(key, value)
                    }
                }
            }
        }
        restorePriceMap.forEach { phone, price ->
            assert(priceMap[phone] == price)
        }

    }

    data class UserData(var id: Int? = null, var username: String? = null) : DataX {

        override fun putToOffice(office: DataXOffice) {
            office.putInt(id)
            office.putString(username)
        }

        override fun applyFromOffice(office: DataXOffice) {
            id = office.getInt()
            username = office.getString()
        }

        override fun toString(): String {
            return "UserData(id=$id, username='$username')"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as UserData

            if (id != other.id) return false
            if (username != other.username) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id ?: 0
            result = 31 * result + (username?.hashCode() ?: 0)
            return result
        }


    }


}