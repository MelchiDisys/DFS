package com.example.dfs.language

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.dfs.LoginScreenActivity
import com.example.dfs.R
import com.example.dfs.databinding.ActivityMainBinding
import com.example.dfs.databinding.ActivitySelectLanguageBinding

class SelectLanguageActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivitySelectLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_language)
        handleLanguageButtons()

    }

    private fun handleLanguageButtons() {

        dataBinding.EnglishBtn.setOnClickListener {

            handleNavigation()
        }
        dataBinding.hindiBtn.setOnClickListener {

            handleNavigation()
        }
        dataBinding.BhojpuriBtn.setOnClickListener {

            handleNavigation()
        }
        dataBinding.MaithiliBtn.setOnClickListener {

            handleNavigation()
        }

    }

    private fun handleNavigation() {
        var intent = Intent(this@SelectLanguageActivity,LoginScreenActivity::class.java)
        startActivity(intent)
    }
}