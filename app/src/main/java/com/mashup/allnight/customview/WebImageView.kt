package com.mashup.allnight.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.util.AttributeSet
import android.widget.ImageView
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
        private class DownloadImageTask(val wiv: WebImageView): AsyncTask<String, Void, Bitmap?>() {
            override fun doInBackground(vararg p0: String?): Bitmap? {
                var bmp: Bitmap? = null
                try {
                    val inputStream = URL(p0[0]).openStream()
                    bmp = BitmapFactory.decodeStream(inputStream)
                } catch(ee: Exception) {
                    ee.printStackTrace()
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
        }

        interface IWebImageViewLoadListener {
            fun onWebImageLoaded(bitmapDrawable: BitmapDrawable)
        }
    }
}