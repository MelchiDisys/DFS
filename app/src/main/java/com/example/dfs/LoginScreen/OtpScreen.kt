package com.example.dfs.LoginScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.dfs.HomeScreen.HomeScreen
import com.example.dfs.R
import com.example.dfs.databinding.OtpVerificationLayBinding

class OtpScreen : AppCompatActivity() {
    private lateinit var dataBinding: OtpVerificationLayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.otp_verification_lay)
        intializeViews()

    }

    private fun intializeViews() {
        dataBinding.apply {

            bottomLay.verifyContinue.setOnClickListener {
                val intent = Intent(this@OtpScreen, HomeScreen::class.java)
                startActivity(intent)
            }

        }

    }
    override fun onBackPressed() {
        return
    }

}