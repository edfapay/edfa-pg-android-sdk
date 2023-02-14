package com.expresspay.sdk.views.expresscardpay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.expresspay.sdk.databinding.ActivityExpressCardPayBinding

internal class ExpressCardPayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpressCardPayBinding
    var expressCardPay: ExpressCardPay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpressCardPayBinding.inflate(
            layoutInflater)
        setContentView(binding.root)

        loadFragment()
        expressCardPay = ExpressCardPay.shared()
    }

    override fun onResume() {
        super.onResume()
        ExpressCardPay.shared()?._onPresent?.let { it(this) }
    }

    private fun loadFragment(){
        supportFragmentManager
            .beginTransaction()
            .add(binding.container.id, ExpressCardPayFragment(), ExpressCardPayFragment::class.java.name)
            .commit()
    }
}