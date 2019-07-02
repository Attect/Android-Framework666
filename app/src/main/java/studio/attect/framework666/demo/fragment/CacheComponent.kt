package studio.attect.framework666.demo.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.component_cache.*
import net.steamcrafted.materialiconlib.IconValue
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import org.msgpack.core.MessagePack
import studio.attect.framework666.DataXOffice
import studio.attect.framework666.FragmentX
import studio.attect.framework666.Logcat
import studio.attect.framework666.cache.CacheDataX
import studio.attect.framework666.cache.CacheManager
import studio.attect.framework666.compomentX.ComponentX
import studio.attect.framework666.compomentX.ComponentXCompanion
import studio.attect.framework666.demo.R
import studio.attect.framework666.demo.cache.TestLargeTestDataX
import studio.attect.framework666.extensions.deleteCacheDataX
import studio.attect.framework666.extensions.runOnUiThread
import studio.attect.framework666.extensions.toDataSizeString
import studio.attect.framework666.extensions.writeCacheDataX
import studio.attect.framework666.interfaces.DataX
import java.io.File
import java.io.FileOutputStream

class CacheComponent : FragmentX() {

    private var persistentCache = PersistentCache()
    private var persistentCacheViewEventLock = false

    private var editCache = EditCache()
    private var hasLastEditCache = false

    private var checkLargeCacheStartTime = 0L
    private var readLargeCacheStartTime = 0L

    private var checkMassCacheStartTime = 0L
    private var checkMassCacheTimes = 0
    private var readMassCacheStartTime = 0L
    private var readMassCacheTimes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cacheDataXViewModel.fastCacheDataXResultList.observe(this, Observer {
            val requestLoadCache = ArrayList<Pair<String, Class<out DataX>>>()
            it.forEach { cacheDataX ->
                when (cacheDataX.tag) {
                    TAG_PERSISTENT_CACHE -> requestLoadCache.add(Pair(TAG_PERSISTENT_CACHE, PersistentCache::class.java))
                    TAG_EDIT_CACHE -> {
                        hasLastEditCache = true
                        recoverEditCache?.visibility = View.VISIBLE
                    }
                    TAG_LARGE_CACHE -> {
                        enableLargeCacheTestBtn(true)
                        largeCacheProgressBar?.isIndeterminate = false
                        val endTime = System.currentTimeMillis()
                        largeCacheCheckInfo.text = "检查完成，耗时" + (endTime - checkLargeCacheStartTime) + "ms"
                    }

                }
                if (cacheDataX.tag.startsWith(TAG_MASS_CACHE)) {
                    checkMassCacheTimes++
                    if (checkMassCacheTimes == MASS_CACHE_TIMES) {
                        enableMassCacheTestBtn(true)
                        val endTime = System.currentTimeMillis()
                        massCacheCheckInfo.text = "检查完成" + checkMassCacheTimes + "个文件，耗时" + (endTime - checkMassCacheStartTime) + "ms"
                    }
                    massCacheProgressBar.progress = checkMassCacheTimes
                }
            }
            readCacheX(requestLoadCache)
        })
        cacheDataXViewModel.cacheDataX.observe(this, Observer {
            when (it?.first) {
                TAG_PERSISTENT_CACHE -> {
                    if (it.second is PersistentCache) persistentCache = it.second as PersistentCache
                    refreshPersistentCache()
                }
                TAG_EDIT_CACHE -> {
                    if (it.second is EditCache) {
                        (it.second as EditCache).apply {
                            editCache.content = content
                            //注意此时View已经不存在了
                            textInputEditText?.setText(content)
                            recoverEditCache?.visibility = View.GONE
                        }
                    }
                }
                TAG_LARGE_CACHE -> {
                    if (it.second is TestLargeTestDataX) {
                        enableLargeCacheTestBtn(true)
                        largeCacheProgressBar?.isIndeterminate = false
                        val endTime = System.currentTimeMillis()
                        largeCacheReadInfo.text = "读取完成，耗时" + (endTime - readLargeCacheStartTime) + "ms"
                    }
                }

            }
            if (it?.first?.startsWith(TAG_MASS_CACHE) == true) {
                readMassCacheTimes++
                if (readMassCacheTimes == MASS_CACHE_TIMES) {
                    enableMassCacheTestBtn(true)
                    val endTime = System.currentTimeMillis()
                    massCacheReadInfo.text = "读取完成" + MASS_CACHE_TIMES + "个文件，耗时" + (endTime - readMassCacheStartTime) + "ms"
                }
                massCacheProgressBar.progress = readMassCacheTimes
            }
        })

        checkCacheX(TAG_PERSISTENT_CACHE, TAG_EDIT_CACHE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.component_cache, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshPersistentCache()

        cacheA.setOnCheckedChangeListener { _, isChecked ->
            persistentCache.a = isChecked
            if (!persistentCacheViewEventLock) savePersistentCache()
        }
        cacheB.setOnCheckedChangeListener { _, isChecked ->
            persistentCache.b = isChecked
            if (!persistentCacheViewEventLock) savePersistentCache()
        }
        cacheC.setOnCheckedChangeListener { _, isChecked ->
            persistentCache.c = isChecked
            if (!persistentCacheViewEventLock) savePersistentCache()
        }
        cacheD.setOnCheckedChangeListener { _, isChecked ->
            persistentCache.d = isChecked
            if (!persistentCacheViewEventLock) savePersistentCache()
        }
        cacheE.setOnCheckedChangeListener { _, isChecked ->
            persistentCache.e = isChecked
            if (!persistentCacheViewEventLock) savePersistentCache()
        }
        cacheF.setOnCheckedChangeListener { _, isChecked ->
            persistentCache.f = isChecked
            if (!persistentCacheViewEventLock) savePersistentCache()
        }

        textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    editCache.content = it.toString()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        if (hasLastEditCache) recoverEditCache.visibility = View.VISIBLE
        recoverEditCache.setOnClickListener {
            recoverEditCache.setText(R.string.loading)
            readCacheX(arrayListOf(Pair(TAG_EDIT_CACHE, EditCache::class.java)))
        }

        writeLargeCacheButton.setOnClickListener {
            createLargeCache()
        }

        checkLargeCacheButton.setOnClickListener {
            enableLargeCacheTestBtn(false)
            largeCacheProgressBar?.isIndeterminate = true
            checkLargeCacheStartTime = System.currentTimeMillis()
            checkCacheX(TAG_LARGE_CACHE)
        }

        readLargeCacheButton.setOnClickListener {
            enableLargeCacheTestBtn(false)
            largeCacheProgressBar?.isIndeterminate = true
            readLargeCacheStartTime = System.currentTimeMillis()
            readCacheX(arrayListOf(Pair(TAG_LARGE_CACHE, TestLargeTestDataX::class.java)))
        }

        deleteLargeCacheButton.setOnClickListener {
            requireContext().deleteCacheDataX(TAG_LARGE_CACHE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            massCacheProgressBar.min = 0
        }
        massCacheProgressBar.max = MASS_CACHE_TIMES

        writeMassCacheButton.setOnClickListener {
            createMassCache()
        }

        checkMassCacheButton.setOnClickListener {
            enableMassCacheTestBtn(false)
            largeCacheProgressBar.progress = 0
            checkMassCacheStartTime = System.currentTimeMillis()
            checkCacheX(List(MASS_CACHE_TIMES) { TAG_MASS_CACHE + it })
        }

        readMassCacheButton.setOnClickListener {
            enableMassCacheTestBtn(false)
            largeCacheProgressBar.progress = 0
            readMassCacheStartTime = System.currentTimeMillis()
            readCacheX(List(MASS_CACHE_TIMES) { Pair(TAG_MASS_CACHE + it, PersistentCache::class.java) })
        }

        deleteMassCacheButton.setOnClickListener {
            enableMassCacheTestBtn(false)
            Thread() {
                List(MASS_CACHE_TIMES) { TAG_MASS_CACHE + it }.forEach {
                    context?.deleteCacheDataX(it)
                }

                runOnUiThread(Runnable {
                    enableMassCacheTestBtn(true)
                })
            }.start()
        }

    }

    override fun onStop() {
        super.onStop()
        if (TextUtils.isEmpty(editCache.content)) {
            if (requireContext().deleteCacheDataX(TAG_EDIT_CACHE)) {
                Logcat.d("删除编辑缓存成功")
            } else {
                Logcat.d("删除编辑缓存失败")
            }
        } else {
            saveEditCache()
        }
    }

    private fun savePersistentCache() {
        requireContext().writeCacheDataX(TAG_PERSISTENT_CACHE, CacheDataX.STORE_TYPE_AUTO, persistentCache)
    }

    private fun refreshPersistentCache() {
        persistentCacheViewEventLock = true
        cacheA?.isChecked = persistentCache.a
        cacheB?.isChecked = persistentCache.b
        cacheC?.isChecked = persistentCache.c
        cacheD?.isChecked = persistentCache.d
        cacheE?.isChecked = persistentCache.e
        cacheF?.isChecked = persistentCache.f
        persistentCacheViewEventLock = false
    }

    private fun saveEditCache() {
        requireContext().writeCacheDataX(TAG_EDIT_CACHE, CacheDataX.STORE_TYPE_SKETCH, editCache)
    }

    private fun enableLargeCacheTestBtn(enable: Boolean) {
        writeLargeCacheButton?.isEnabled = enable
        checkLargeCacheButton?.isEnabled = enable
        readLargeCacheButton?.isEnabled = enable
        deleteLargeCacheButton?.isEnabled = enable
    }

    private fun enableMassCacheTestBtn(enable: Boolean) {
        writeMassCacheButton?.isEnabled = enable
        checkMassCacheButton?.isEnabled = enable
        readMassCacheButton?.isEnabled = enable
        deleteMassCacheButton?.isEnabled = enable
    }

    private fun createLargeCache() {
        enableMassCacheTestBtn(false)
        largeCacheWriteInfo.setText(R.string.working)
        val file = File(CacheManager.getCacheFileName(requireContext(), TAG_LARGE_CACHE))
        val outputStream = FileOutputStream(file)
        val office = DataXOffice(MessagePack.newDefaultPacker(outputStream))
        val largeData = TestLargeTestDataX()
        val cacheDataX = CacheDataX(largeData).apply {
            tag = TAG_LARGE_CACHE
            storeType = CacheDataX.STORE_TYPE_AUTO
        }
        largeCacheProgressBar?.isIndeterminate = true
        Thread() {
            val startTime = System.currentTimeMillis()
            cacheDataX.putToOffice(office)
            val endTime = System.currentTimeMillis()
            runOnUiThread(Runnable {
                largeCacheWriteInfo.text = "写入完成，耗时" + (endTime - startTime) + "ms 大小:" + file.length().toDataSizeString()
                enableLargeCacheTestBtn(true)
                largeCacheProgressBar?.isIndeterminate = false
            })
        }.start()
    }

    private fun createMassCache() {
        enableMassCacheTestBtn(false)
        massCacheWriteInfo.setText(R.string.working)
        val data = PersistentCache()
        Thread() {

            val startTime = System.currentTimeMillis()
            for (i in 0 until MASS_CACHE_TIMES) {
                requireContext().writeCacheDataX(TAG_MASS_CACHE + i, CacheDataX.STORE_TYPE_SKETCH, data)
                runOnUiThread(Runnable {
                    massCacheProgressBar.progress = i + 1
                })
            }
            val endTime = System.currentTimeMillis()
            runOnUiThread(Runnable {
                massCacheWriteInfo.text = "写入完成，耗时" + (endTime - startTime) + "ms 数量:" + MASS_CACHE_TIMES
                enableMassCacheTestBtn(true)
            })
        }.start()

    }

    private fun getStorageManager(): StorageManager? {
        context?.let { c ->
            val service = c.getSystemService(Context.STORAGE_SERVICE)
            if (service is StorageManager) return service
        }
        return null
    }

    /**
     * 持久缓存数据
     * 六个Checkbox的数据
     */
    class PersistentCache : DataX {
        var a = false
        var b = false
        var c = false
        var d = false
        var e = false
        var f = false

        override fun putToOffice(office: DataXOffice) {
            office.putBoolean(a)
            office.putBoolean(b)
            office.putBoolean(c)
            office.putBoolean(d)
            office.putBoolean(e)
            office.putBoolean(f)
        }

        override fun applyFromOffice(office: DataXOffice) {
            office.getBoolean()?.let { a = it }
            office.getBoolean()?.let { b = it }
            office.getBoolean()?.let { c = it }
            office.getBoolean()?.let { d = it }
            office.getBoolean()?.let { e = it }
            office.getBoolean()?.let { f = it }
        }
    }

    /**
     * 编辑数据
     */
    class EditCache : DataX {

        var content = ""

        override fun putToOffice(office: DataXOffice) {
            office.putString(content)
        }

        override fun applyFromOffice(office: DataXOffice) {
            office.getString()?.let { content = it }
        }


    }

    companion object : ComponentXCompanion {

        const val TAG_PERSISTENT_CACHE = "cacheTest_persistentCache"

        const val TAG_EDIT_CACHE = "cacheTest_editCache"

        const val TAG_LARGE_CACHE = "cacheTest_largeCache"

        const val TAG_MASS_CACHE = "cacheTest_massCache"

        /**
         * 大量缓存测试读写次数
         */
        const val MASS_CACHE_TIMES = 100

        override fun getTag(): String = "cache_test"

        override fun getIcon(context: Context?): Drawable? {
            context?.let {
                return MaterialDrawableBuilder.with(context).setIcon(IconValue.FILE_UNDO).setSizeDp(24).setColor(Color.WHITE).build()
            }
            return null
        }

        override fun getName(context: Context?): String {
            context?.let {
                return it.getString(R.string.cache_component_title)
            }
            return "缓存测试"
        }

        override fun newInstance(): ComponentX = CacheComponent()

    }
}