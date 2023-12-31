package com.edfapg.sdk.views.edfacardpay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edfapg.sdk.databinding.ActivityEdfaCardPayBinding

internal class EdfaCardPayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEdfaCardPayBinding
    var edfaCardPay: EdfaCardPay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEdfaCardPayBinding.inflate(
            layoutInflater)
        setContentView(binding.root)

        loadFragment()
        edfaCardPay = EdfaCardPay.shared()
    }

    override fun onResume() {
        super.onResume()
        EdfaCardPay.shared()?._onPresent?.let { it(this) }
    }

    private fun loadFragment(){
        supportFragmentManager
            .beginTransaction()
            .add(binding.container.id, EdfaCardPayFragment(), EdfaCardPayFragment::class.java.name)
            .commit()
    }
}