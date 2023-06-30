package com.example.dfs.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dfs.R
import com.example.dfs.language.SelectLanguageActivity


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        Handler().postDelayed(Runnable
        { //This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashScreen, SelectLanguageActivity::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 3000)
    }
    }
