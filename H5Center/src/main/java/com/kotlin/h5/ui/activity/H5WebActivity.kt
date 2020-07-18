package com.kotlin.h5.ui.activity

import android.content.Context
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.h5.R
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.activity_h5_web.*

class H5WebActivity : AppCompatActivity() {
    private lateinit  var web: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5_web)
        web=mH5WebView;
        initWebSetting()
        web.loadUrl("https://www.baidu.com");
    }

    private fun initWebSetting() {
        var settings=web.settings;
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.allowFileAccess = true
        settings.javaScriptEnabled=true;
        settings.allowFileAccess = true
        settings.domStorageEnabled = true
        //设置缩放
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        //启用地理定位
        settings.setGeolocationEnabled(true)
        settings.textZoom = 100
//        web.setWebChromeClient(chromeClient)
//        web.setWebViewClient(client)
        web.addJavascriptInterface(JsInterFace(this), "JsInterFace")
    }
    private class JsInterFace(var context: Context) {
        @JavascriptInterface
        fun outLogin() {

        }

        @get:JavascriptInterface
        val userId: Unit
            get() {

            }

        @get:JavascriptInterface
        val location: Unit
            get() {
//                toLocation()
            }

        @JavascriptInterface
        fun back() {
//          this@H5WebActivity.finish()
        }

    }

    override fun onStop() {
        super.onStop()
        if (web != null) {
            web.settings.javaScriptEnabled = false
        }
    }

    override fun onDestroy() {
        try {
            if (web != null) {
                web.stopLoading()
                web.destroy()

            }
        } catch (e: Exception) {
        }
//        mLocationClient.onDestroy()
        super.onDestroy()
    }

}