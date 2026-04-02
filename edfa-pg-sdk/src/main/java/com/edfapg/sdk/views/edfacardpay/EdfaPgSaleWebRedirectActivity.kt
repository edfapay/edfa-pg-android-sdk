/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.views.edfacardpay

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.SafeBrowsingResponse
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.edfapg.sdk.R
import com.edfapg.sdk.databinding.ActivityEdfapayWebBinding
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsSuccess
import com.edfapg.sdk.model.response.sale.EdfaPgSaleRedirect
import com.edfapg.sdk.toolbox.EdfaPgUtil
import com.edfapg.sdk.toolbox.userConfirmation
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

private var onEdfaPgSaleWebRedirectActivityResult:((result: EdfaPgGetTransactionDetailsSuccess?, error:EdfaPgError?) ->Unit)? = null
class EdfaPgSaleWebRedirectActivity : AppCompatActivity(R.layout.activity_edfapay_web) {

    internal lateinit var binding: ActivityEdfapayWebBinding
    private var transactionData:CardTransactionData? = null
    private var processCompleted:Boolean = false
    private var currentRedirect: EdfaPgSaleRedirect? = null

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEdfapayWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            transactionData = intent.getSerializableExtra(DATA, CardTransactionData::class.java)
        } else {
            transactionData = intent.getSerializableExtra(DATA) as CardTransactionData?
        }

        binding.webView.apply {
            settings.defaultTextEncodingName = "utf-8"
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            webViewClient = object : WebViewClient() {

                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    val redirect = currentRedirect
                    if (redirect != null && request?.url?.toString() == redirect.redirectUrl) {
                        return try {
                            val json = JSONObject().apply {
                                put("body", redirect.redirectParams.body)
                            }.toString()
                            val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
                            val okRequest = Request.Builder()
                                .url(redirect.redirectUrl)
                                .post(body)
                                .build()
                            val response = okHttpClient.newCall(okRequest).execute()
                            val html = response.body?.string()
                            if (html != null) {
                                WebResourceResponse("text/html", "UTF-8", html.byteInputStream())
                            } else {
                                null
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                    return super.shouldInterceptRequest(view, request)
                }

                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean {
                    val url = request?.url?.toString().orEmpty()
                    Log.d("xpWebRedirect", ">> >> Redirect URL: $url")
                    if (handleTermUrl3ds(url)) {
                        return true
                    }
                    return false
                }

                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    Log.d("xpWebRedirect", ">> >> Redirect URL: ${url.orEmpty()}")
                    if (handleTermUrl3ds(url.orEmpty())) {
                        return true
                    }
                    return false
                }

                private fun handleTermUrl3ds(url: String): Boolean {
                    if (!processCompleted && url.startsWith(EdfaPgUtil.ProcessCompleteCallbackUrl)) {
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
                    handler?.userConfirmation(
                        this@EdfaPgSaleWebRedirectActivity,
                        ok = {
                            handler.cancel()
                        }
                    )
                }
            }

            binding.progressBar.show()

            transactionData?.response?.let {
                getAndLoadHtml(it)
            }
        }
    }

    fun getAndLoadHtml(redirect: EdfaPgSaleRedirect) {
        currentRedirect = redirect
        binding.webView.loadUrl(redirect.redirectUrl)
    }

    fun operationCompleted(result: EdfaPgGetTransactionDetailsSuccess?, error:EdfaPgError?){
        val intent = Intent().putExtra("result", result).putExtra("error", error)
        setResult(Activity.RESULT_OK, intent)
        onEdfaPgSaleWebRedirectActivityResult?.let { it(result,error) }
        finish()
    }

    companion object {

        private const val DATA = "DATA"

        fun intent(
            context: Context,
            cardTransactionData: CardTransactionData,
            onResult: ((result: EdfaPgGetTransactionDetailsSuccess?, error:EdfaPgError?) -> Unit)? = null
        ) : Intent{
            onEdfaPgSaleWebRedirectActivityResult = onResult
            return Intent(context, EdfaPgSaleWebRedirectActivity::class.java).apply {
                putExtra(DATA, cardTransactionData)
            }
        }
    }
}