package com.mashup.allnight.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.util.AttributeSet
import android.widget.ImageView
import androidx.collection.LruCache
import java.net.URL

class WebImageView
    : ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    private val webImageLoadedListeners = ArrayList<IWebImageViewLoadListener>()
    private var downloadImageTask: DownloadImageTask? = null
    var imgUrl : String = ""
        set(value) {
            field = value
            loadImage()
        }

    init {
        webImageLoadedListeners.clear()
    }


    private fun loadImage() {
        if(imgUrl.isEmpty()) return

        setImageDrawable(null)

        downloadImageTask?.let {
            if (!it.isCancelled) it.cancel(true)
        }
        downloadImageTask = DownloadImageTask(this)
        downloadImageTask?.run {
            this.execute(imgUrl)
        }
    }

    fun addOnWebImageLoadedListener(listener: IWebImageViewLoadListener) {
        webImageLoadedListeners.add(listener)
    }
    fun removeOnWebImageLoadedListener(listener: IWebImageViewLoadListener) {
        webImageLoadedListeners.remove(listener)
    }
    fun clearOnWebImageLoadedListener() {
        webImageLoadedListeners.clear()
    }

    companion object {
        private val lruCache = LruCache<String, Bitmap>(100) //use LruCache (memory cache) to save image temporary (100 items)

        private class DownloadImageTask(val wiv: WebImageView): AsyncTask<String, Void, Bitmap?>() {
            override fun doInBackground(vararg p0: String?): Bitmap? {
                var bmp: Bitmap? = loadImageFromMemCache(p0[0]!!)

                if (bmp == null) {
                    try {
                        val inputStream = URL(p0[0]).openStream()
                        bmp = BitmapFactory.decodeStream(inputStream)
                        saveImageToMemCache(p0[0]!!, bmp)
                    } catch (ee: Exception) {
                        ee.printStackTrace()
                    }
                }
                return bmp

            }

            override fun onPostExecute(result: Bitmap?) {
                val bmp = BitmapDrawable(wiv.resources, result)
                wiv.setImageDrawable(bmp)

                for (listener: IWebImageViewLoadListener in wiv.webImageLoadedListeners)
                    listener.onWebImageLoaded(bmp)
                super.onPostExecute(result)
            }


            fun loadImageFromMemCache(id: String): Bitmap? {
                return lruCache.get(id)
            }

            fun saveImageToMemCache(id: String, bmp: Bitmap) {
                lruCache.put(id, bmp)
            }
        }

        interface IWebImageViewLoadListener {
            fun onWebImageLoaded(bitmapDrawable: BitmapDrawable)
        }
    }
}