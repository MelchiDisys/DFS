package com.example.dfs.LoginScreen

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.dfs.Api.ApiUtilis
import com.example.dfs.LoginScreen.model.LoginModel
import com.example.dfs.LoginScreen.viewModel.LoginViewModel
import com.example.dfs.R
import com.example.dfs.databinding.ActivityMainBinding
import com.example.dfs.preferences.Pref_storage
import com.example.dfs.utils.Status
import com.example.dfs.utils.ViewModelFactory
import com.google.gson.JsonObject
import java.util.*


class LoginScreenActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dataBinding: ActivityMainBinding
    var mobileNumber:String = ""

    lateinit var textToSpeech:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        dataBinding.bottomLay.generateOtp.setOnClickListener {
            handleNavigation()
        }
        setUpViewModel()
        intializeViews()
    }

    override fun onBackPressed() {
       return
    }

  fun handlePostApi(){
      val jsonObject = JsonObject()
      jsonObject.addProperty("mobile", mobileNumber)

      loginViewModel.postLoginResponse(jsonObject).observe(this
      ) {
          it.let { resource ->
              when (resource.status) {
                  Status.LOADING -> {
                      dataBinding.progressBar.visibility = View.VISIBLE
                  }
                  Status.SUCCESS -> {
                      saveDataInternally(it.data)
                      dataBinding.progressBar.visibility = View.GONE


                  }
                  Status.ERROR -> {
                      Log.e("error", "Error is captured")
                  }
              }

          }

      }
  }

    private fun saveDataInternally(data: LoginModel?) {
        Pref_storage.setDetail(this, "id", data?.result?.id.toString())
        Pref_storage.setDetail(this, "displayName", data?.result?.displayName.toString())
        handleNavigation()
    }

    private fun handleNavigation() {
        var intent = Intent(this@LoginScreenActivity,OtpScreen::class.java)
        startActivity(intent)
    }

    private fun intializeViews() {
        textToSpeech = TextToSpeech(
            applicationContext
        ) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language =  Locale("hi")
            }
        }


        dataBinding.apply {

           bottomLay.generateOtp.setOnClickListener {

               textToSpeech.speak(getString(R.string.generate_otp), TextToSpeech.QUEUE_FLUSH, null)

               mobileNumber = dataBinding.bottomLay.phoneNumberEdtxt.text.toString()

               if (!mobileNumber.isNullOrBlank() && mobileNumber.length == 10){
                   handleNavigation()
               }else{
                   Toast.makeText(this@LoginScreenActivity,getString(R.string.enter_valid_mobile_no), Toast.LENGTH_LONG).show()
               }

           }


        } }

    private fun setUpViewModel() {
        loginViewModel = ViewModelProvider(
            this, ViewModelFactory(ApiUtilis().getAPIServiceInKotlin(), this.application)
        )[LoginViewModel::class.java]


    }
}