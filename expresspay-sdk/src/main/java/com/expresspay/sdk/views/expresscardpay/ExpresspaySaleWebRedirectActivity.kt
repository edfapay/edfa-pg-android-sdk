/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.views.expresscardpay

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.expresspay.sdk.R
import com.expresspay.sdk.databinding.ActivityExpresspayWebBinding
import com.expresspay.sdk.model.response.base.error.ExpresspayError
import com.expresspay.sdk.model.response.gettransactiondetails.ExpresspayGetTransactionDetailsSuccess
import com.expresspay.sdk.toolbox.ExpresspayUtil
import com.expresspay.sdk.toolbox.serializable
import java.net.URLEncoder

class ExpresspaySaleWebRedirectActivity : AppCompatActivity(R.layout.activity_expresspay_web) {

    private lateinit var binding: ActivityExpresspayWebBinding
    private var transactionData:CardTransactionData? = null
    private var processCompleted:Boolean = false

    @SuppressLint("SetJavaScriptEnabled")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExpresspayWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            transactionData = intent.getSerializableExtra(DATA, CardTransactionData::class.java)
        } else {
            transactionData = intent.getSerializableExtra(DATA) as CardTransactionData?
        }

        binding.webView.apply {
            settings.defaultTextEncodingName = "utf-8"

            webViewClient = object : WebViewClient() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean {
                    Log.d("xpWebRedirect", ">> >> Redirect URL: ${request?.url?.toString().orEmpty()}")
                    if (handleTermUrl3ds(request?.url?.toString().orEmpty())) {
                        return false
                    }

                    return super.shouldOverrideUrlLoading(view, request)
                }

                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    Log.d("xpWebRedirect", ">> >> Redirect URL: ${url.orEmpty()}")
                    if (handleTermUrl3ds(url.orEmpty())) {
                        return false
                    }

                    return super.shouldOverrideUrlLoading(view, url)
                }

                private fun handleTermUrl3ds(url: String): Boolean {
                    val url_ = url.replace("?" , "")
                    if (!processCompleted && url_ == ExpresspayUtil.ProcessCompleteCallbackUrl) {
                        processCompleted = true
                        checkTransactionStatus(transactionData!!)
                        return true
                    }

                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBar.show()
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.hide()
                    super.onPageFinished(view, url)
                }

                @Deprecated("Deprecated in Java")
                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?,
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?,
                ) {
                    super.onReceivedError(view, request, error)
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?,
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                }

                override fun onSafeBrowsingHit(
                    view: WebView?,
                    request: WebResourceRequest?,
                    threatType: Int,
                    callback: SafeBrowsingResponse?,
                ) {
                    super.onSafeBrowsingHit(view, request, threatType, callback)
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
//                    super.onReceivedSslError(view, handler, error)
                    handler?.proceed()
                }
            }

            binding.progressBar.show()

            /* Enable Javascript in Webview */
            settings.javaScriptEnabled = true
            transactionData?.response?.let {
                val postData = "body=" + URLEncoder.encode(it.redirectParams.body, "UTF-8")
                if(it.redirectMethod == "POST"){
                    postUrl(it.redirectUrl, postData.toByteArray())
                }else{
                    loadUrl("${it.redirectUrl}?$postData")
                }
            }
        }
    }

    fun operationCompleted(result: ExpresspayGetTransactionDetailsSuccess?, error:ExpresspayError?){
        val intent = Intent().putExtra("result", result).putExtra("error", error)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {

        private const val DATA = "DATA"

        fun intent(
            context: Activity,
            cardTransactionData: CardTransactionData,
        ) : Intent{
            return Intent(context, ExpresspaySaleWebRedirectActivity::class.java).apply {
                putExtra(DATA, cardTransactionData)
            }
        }
    }
}
