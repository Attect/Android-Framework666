package studio.attect.framework666

import com.google.gson.Gson
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
    fun getBooleanList() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val list = arrayListOf(true, false, true, null, true, true)
        dataXOffice.putBooleanList(list)
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        dataXOffice.getBooleanList()?.let { newList ->
            list.forEachIndexed { index, value ->
                assert(value == newList[index])
            }
        }
    }

    @Test
    fun getBooleanEmptyList() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val list = arrayListOf<Boolean?>()
        dataXOffice.putBooleanList(list)
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        dataXOffice.getBooleanList()?.let { newList ->
            list.forEachIndexed { index, value ->
                assert(value == newList[index])
            }
        }
    }

    @Test
    fun getBooleanNullList() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val list: ArrayList<Boolean?>? = null
        dataXOffice.putBooleanList(list)
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        assert(dataXOffice.getBooleanList() == null)
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
//        val userData = UserData()
//        val packer = MessagePack.newDefaultBufferPacker()
//        val dataXOffice = DataXOffice(packer)
//        userData.putToOffice(dataXOffice)
//        val box = packer.toByteArray()
//        dataXOffice.unpack(box)
//        val newUserData = dataXOffice.getDataX(UserData::class.java)
//        println(newUserData)
//        assert(userData == newUserData)

        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val number: Int? = null

        //putToOffice
        dataXOffice.putInt(number)
        dataXOffice.putInt(123)
        dataXOffice.putInt(1)
        dataXOffice.putInt(null)
        dataXOffice.putString(null)
        dataXOffice.putString("")
        dataXOffice.putString("123")
        dataXOffice.putDouble(513.13)
        dataXOffice.putDouble(null)

        //applyFromOffice
        val box = packer.toByteArray()
        dataXOffice.unpack(box)

        println(dataXOffice.getInt())
        println(dataXOffice.getInt())
        println(dataXOffice.getInt())
        println(dataXOffice.getInt())
        println(dataXOffice.getString())
        println(dataXOffice.getString())
        println(dataXOffice.getString())
        println(dataXOffice.getDouble())
        println(dataXOffice.getDouble())

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
    fun getInnerDataX() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val userData = InnerUserData(123456, "Attect")
        dataXOffice.putDataX(userData)
        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        val restoreUserData = dataXOffice.getDataX(InnerUserData::class.java, this)
        assert(userData == restoreUserData)
    }

    @Test
    fun putMap() {
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val priceMap = HashMap<String, Double>()
        priceMap["OnePlus5"] = 2999.0
        priceMap["Huawei P30"] = 3988.0
        priceMap["Redmi"] = 799.0

        dataXOffice.putMap(priceMap)
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
        priceMap["OnePlus5"] = 2999.0
        priceMap["Huawei P30"] = 3988.0
        priceMap["Redmi"] = 799.0

        dataXOffice.putMap(priceMap)
        priceMap.forEach { (phone, price) ->
            dataXOffice.putString(phone)
            dataXOffice.putDouble(price)
        }

        val box = packer.toByteArray()
        dataXOffice.unpack(box)
        val restorePriceMap = HashMap<String, Double>()
        val mapInfo = dataXOffice.getMap(String::class.java, Double::class.java)
        mapInfo?.let { data ->
            val map = data.second
            for (i in 0 until data.first) {
                map.put(dataXOffice.getString(), dataXOffice.getDouble())
            }

        }

        restorePriceMap.forEach { (phone, price) ->
            assert(priceMap[phone] == price)
        }

    }

    @Test
    fun put() {
        val gson = Gson()
        val packer = MessagePack.newDefaultBufferPacker()
        val dataXOffice = DataXOffice(packer)
        val userData = UserData(6385678, "Attect")
        val book = userData.book
        book.name = "C+++"
        book.page = 1000
        userData.book = book
        userData.score = arrayListOf(1, 2, 3)
        userData.backpack = arrayListOf(userData.newBook("Chinese", 5000), userData.newBook("Math", 200))
        userData.cards = arrayListOf(arrayListOf(Card("joker", "R"), Card("Faker", "R")), arrayListOf(Card("Tom", "SR"), Card("Jerry", "SR")))
        userData.map.apply {
            put("广西", "南宁")
            put("广东", "深圳")
            put("美国", "新乡")
        }
        userData.cityMap.apply {
            put("中国", arrayListOf("北京", "上海", "成都", "长沙"))
            put("美国", arrayListOf("新乡", "西单"))
            put("韩国", arrayListOf("汉城"))
        }
        dataXOffice.put(userData)
        val jsonString = gson.toJson(userData)
        println("json[${jsonString.length}]:$jsonString")
        println("put total data:${dataXOffice.backLog()}")
        val box = packer.toByteArray()
        println("force string:" + String(box))
        dataXOffice.unpack(box)
        val newUserData = dataXOffice.get(UserData::class.java)
        println(userData)
        println(newUserData)
        assert(userData == newUserData)
    }

    class UserData constructor() : DataX {
        constructor(id: Int? = null, username: String? = null) : this() {
            this.id = id
            this.username = username
        }

        var id: Int? = null
        var username: String? = null
        var book: Book = Book()
        var score: ArrayList<Int> = arrayListOf()
        var backpack: ArrayList<Book>? = null
        var cards = ArrayList<ArrayList<Card>>()
        var map = HashMap<String, String>()
        var cityMap = HashMap<String, ArrayList<String>>()

        fun newBook(name: String, page: Int): Book = Book(name, page)

        override fun putToOffice(office: DataXOffice) {
            office.putInt(id)
            office.putString(username)
        }

        override fun applyFromOffice(office: DataXOffice) {
            id = office.getInt()
            username = office.getString()
        }


        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as UserData

            if (id != other.id) return false
            if (username != other.username) return false
            if (book != other.book) return false
            return true
        }

        override fun hashCode(): Int {
            var result = id ?: 0
            result = 31 * result + (username?.hashCode() ?: 0)
            return result
        }

        override fun toString(): String {
            return "UserData(id=$id, username=$username, book=$book, score=$score, backpack=$backpack, card=$cards, map=$map, cityMap=$cityMap)"
        }


        inner class Book() {
            constructor(n: String, p: Int) : this() {
                name = n
                page = p
            }

            var name: String = ""
            var page: Int = 0


            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as Book
                if (name != other.name) return false
                if (page != other.page) return false

                return true
            }

            override fun hashCode(): Int {
                var result = name.hashCode()
                result = 33 * result + page
                return result
            }

            override fun toString(): String {
                return "Book(name='$name', page=$page)"
            }
        }
    }

    class Card() {
        constructor(n: String, l: String) : this() {
            name = n
            level = l
        }

        var name = ""
        var level = ""
        val price = 1
        override fun toString(): String {
            return "Card(name='$name', level='$level', price=$price)"
        }


    }



    inner class InnerUserData() : DataX {

        constructor(i: Int? = null, u: String? = null) : this() {
            id = i
            username = u
        }

        var id: Int? = null
        var username: String? = null

        override fun putToOffice(office: DataXOffice) {
            office.putInt(id)
            office.putString(username)
        }

        override fun applyFromOffice(office: DataXOffice) {
            id = office.getInt()
            username = office.getString()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as InnerUserData

            if (id != other.id) return false
            if (username != other.username) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id ?: 0
            result = 32 * result + (username?.hashCode() ?: 0)
            return result
        }
    }


}