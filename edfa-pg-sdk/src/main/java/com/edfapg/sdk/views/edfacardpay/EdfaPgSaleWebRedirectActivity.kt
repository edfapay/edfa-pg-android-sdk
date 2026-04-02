/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.views.edfacardpay

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.edfapg.sdk.R
import com.edfapg.sdk.databinding.ActivityEdfapayWebBinding
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsSuccess
import com.edfapg.sdk.toolbox.EdfaPgUtil
import com.edfapg.sdk.toolbox.userConfirmation
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private var onEdfaPgSaleWebRedirectActivityResult:
    ((result: EdfaPgGetTransactionDetailsSuccess?, error: EdfaPgError?) -> Unit)? = null

class EdfaPgSaleWebRedirectActivity : AppCompatActivity(R.layout.activity_edfapay_web) {

    private lateinit var binding: ActivityEdfapayWebBinding
    private var transactionData: CardTransactionData? = null
    private var processCompleted: Boolean = false

    @Suppress("unused")
    private inner class CollectorBridge(
        private val targetWebView: WebView,
        private val collectorUrl: String,
    ) {
        @JavascriptInterface
        @SuppressLint("JavascriptInterface")
        fun receiveCollectorHtml(base64Utf8: String) {
            val html =
                try {
                    String(Base64.decode(base64Utf8, Base64.DEFAULT), StandardCharsets.UTF_8)
                } catch (e: Exception) {
                    Log.e(TAG, "Collector HTML decode failed", e)
                    return
                }
            targetWebView.post {
                runCatching { targetWebView.removeJavascriptInterface(JS_BRIDGE_NAME) }
                targetWebView.loadDataWithBaseURL(
                    collectorUrl,
                    html,
                    "text/html",
                    StandardCharsets.UTF_8.name(),
                    collectorUrl,
                )
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEdfapayWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionData =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra(DATA, CardTransactionData::class.java)
            } else {
                intent.getSerializableExtra(DATA) as CardTransactionData?
            }

        binding.webView.apply {
            settings.defaultTextEncodingName = "utf-8"
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            }

            webViewClient = object : WebViewClient() {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean {
                    val url = request?.url?.toString().orEmpty()
                    val isMainFrame =
                        Build.VERSION.SDK_INT < Build.VERSION_CODES.N ||
                            request == null ||
                            request.isForMainFrame
                    if (isMainFrame && handleTermUrl3ds(url)) return false
                    return super.shouldOverrideUrlLoading(view, request)
                }

                @Deprecated("Deprecated in Java")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (handleTermUrl3ds(url.orEmpty())) return false
                    return super.shouldOverrideUrlLoading(view, url)
                }

                private fun handleTermUrl3ds(url: String): Boolean {
                    if (processCompleted || !url.startsWith(EdfaPgUtil.ProcessCompleteCallbackUrl)) {
                        return false
                    }
                    processCompleted = true
                    checkTransactionStatus(transactionData!!)
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBar.show()
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.hide()
                    super.onPageFinished(view, url)
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?,
                ) {
                    handler?.userConfirmation(
                        this@EdfaPgSaleWebRedirectActivity,
                        ok = { handler.cancel() },
                    )
                }
            }

            binding.progressBar.show()

            transactionData?.response?.let {
                val postData = "body=" + URLEncoder.encode(it.redirectParams.body, Charsets.UTF_8.name())
                if (it.redirectMethod.equals("POST", ignoreCase = true)) {
                    addJavascriptInterface(CollectorBridge(this, it.redirectUrl), JS_BRIDGE_NAME)
                    loadPostRedirectBootstrap(it.redirectUrl, it.redirectParams.body)
                } else {
                    loadUrl("${it.redirectUrl}?$postData")
                }
            }
        }
    }

    private fun WebView.loadPostRedirectBootstrap(redirectUrl: String, body: String) {
        val base = redirectBaseUrl(redirectUrl)
        val json = JSONObject().put("body", body).toString()
        val b64 = Base64.encodeToString(json.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
        val urlLiteral = JSONObject.quote(redirectUrl)
        val html =
            """
            <!DOCTYPE html>
            <html>
            <head><meta charset="UTF-8"><meta name="viewport" content="width=device-width"></head>
            <body>
            <script>
            (function(){
              var payload = JSON.parse(atob('$b64'));
              fetch($urlLiteral, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                  'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8'
                },
                body: JSON.stringify(payload),
                credentials: 'include',
                redirect: 'follow'
              }).then(function(res) {
                return res.text().then(function(text) {
                  if (!res.ok) {
                    document.documentElement.innerHTML = '<body style="font-family:sans-serif;padding:16px"><pre style="white-space:pre-wrap">' +
                      text.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;') + '</pre></body>';
                    return;
                  }
                  try {
                    $JS_BRIDGE_NAME.receiveCollectorHtml(btoa(unescape(encodeURIComponent(text))));
                  } catch (e) {
                    document.body.innerHTML = '<pre style="white-space:pre-wrap">' + String(e) + '</pre>';
                  }
                });
              }).catch(function(e) {
                document.body.innerHTML = '<pre style="white-space:pre-wrap">' + String(e) + '</pre>';
              });
            })();
            </script>
            </body>
            </html>
            """.trimIndent()
        loadDataWithBaseURL(base, html, "text/html", StandardCharsets.UTF_8.name(), null)
    }

    private fun redirectBaseUrl(redirectUrl: String): String {
        return try {
            val uri = Uri.parse(redirectUrl)
            val scheme = uri.scheme ?: "https"
            val host = uri.host
            if (host.isNullOrEmpty()) {
                redirectUrl
            } else {
                val port = uri.port
                val portPart =
                    if (port != -1 &&
                        ((scheme == "http" && port != 80) || (scheme == "https" && port != 443))
                    ) {
                        ":$port"
                    } else {
                        ""
                    }
                "$scheme://$host$portPart/"
            }
        } catch (e: Exception) {
            redirectUrl
        }
    }

    fun operationCompleted(
        result: EdfaPgGetTransactionDetailsSuccess?,
        error: EdfaPgError?,
    ) {
        val intent = Intent().putExtra("result", result).putExtra("error", error)
        setResult(Activity.RESULT_OK, intent)
        onEdfaPgSaleWebRedirectActivityResult?.invoke(result, error)
        finish()
    }

    companion object {
        private const val DATA = "DATA"
        private const val TAG = "xpWebRedirect"
        private const val JS_BRIDGE_NAME = "EdfaPgCollectorHost"

        fun intent(
            context: Context,
            cardTransactionData: CardTransactionData,
            onResult: ((result: EdfaPgGetTransactionDetailsSuccess?, error: EdfaPgError?) -> Unit)? =
                null,
        ): Intent {
            onEdfaPgSaleWebRedirectActivityResult = onResult
            return Intent(context, EdfaPgSaleWebRedirectActivity::class.java).apply {
                putExtra(DATA, cardTransactionData)
            }
        }
    }
}
