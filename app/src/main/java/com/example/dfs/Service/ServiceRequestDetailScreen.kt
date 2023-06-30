package com.example.dfs.Service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dfs.Api.ApiUtilis
import com.example.dfs.LoginScreen.viewModel.LoginViewModel
import com.example.dfs.PurchaseScreen.PurchaseActivity
import com.example.dfs.R
import com.example.dfs.Service.model.ServiceRequestModel
import com.example.dfs.databinding.ActivityServiceRequestDetailScreenBinding
import com.example.dfs.preferences.Pref_storage
import com.example.dfs.utils.Status
import com.example.dfs.utils.ViewModelFactory

class ServiceRequestDetailScreen : AppCompatActivity() {
    private lateinit var dataBinding: ActivityServiceRequestDetailScreenBinding
    private lateinit var loginViewModel: LoginViewModel
    lateinit var adapter: ServiceAdapter
    var userId = 0
    var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_service_request_detail_screen)
        setUpViewModel()
        intializeViews()
        handleServiceRequestApi()

    }

    private fun setUpViewModel() {
        loginViewModel = ViewModelProvider(this, ViewModelFactory(ApiUtilis().getAPIServiceInKotlin(), this.application))[LoginViewModel::class.java]

    }

    fun handleServiceRequestApi(){
        loginViewModel.getUserServiceRequest(userId).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        handleRecyclerView(it.data)

                    }
                    Status.ERROR -> {
                        Log.e("error", "Error is captured")
                    }
                }

            }

        }
    }

    private fun handleRecyclerView(response: ServiceRequestModel?)
    {
        val layoutManager = LinearLayoutManager(this)
        dataBinding.apply {
            listView.layoutManager = layoutManager
            adapter = ServiceAdapter(this@ServiceRequestDetailScreen, response?.result!!)
            listView.adapter = adapter
        }


    }


    private fun intializeViews() {
        userId = Pref_storage.getDetail( this,"id").toInt()
        userName= Pref_storage.getDetail( this,"displayName")

        dataBinding.apply {
            actionbarAccount.apply {
                cartImg.visibility = View.GONE
                searchTxt.visibility = View.GONE
                displayNameTxt.text = userName

            }

            fab.setOnClickListener {
                val intent = Intent(this@ServiceRequestDetailScreen, PurchaseActivity::class.java)
                startActivity(intent)
            }

        }

    }
}