package com.example.dfs.Service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.dfs.Api.ApiUtilis
import com.example.dfs.LoginScreen.viewModel.LoginViewModel
import com.example.dfs.R
import com.example.dfs.Service.model.ResultX
import com.example.dfs.Service.model.ResultXX
import com.example.dfs.Service.model.ServiceModel
import com.example.dfs.Service.model.ServiceTypeModel
import com.example.dfs.databinding.ActivityServiceRequestMainBinding
import com.example.dfs.preferences.Pref_storage
import com.example.dfs.utils.Status
import com.example.dfs.utils.ViewModelFactory
import com.google.gson.JsonObject
import java.util.ArrayList

class ServiceRequestMain : AppCompatActivity() {
    private lateinit var dataBinding: ActivityServiceRequestMainBinding
    private lateinit var loginViewModel: LoginViewModel

    var serviceTypeList = ArrayList<ResultX>()
    var enteredServiceTypeList: ResultX?=null
    var serviceTypeId: Int=0
    var serviceTypeName: String?=""

    var serviceList = ArrayList<ResultXX>()
    var enteredServiceList: ResultXX?=null
    var serviceId: Int=0
    var serviceName: String?=""

    var serviceProviderList = ArrayList<ResultX>()
    var enteredServiceProviderList: ResultX?=null
    var serviceProviderId: Int=0
    var serviceProviderName: String?=""


    var userId = 0
    var userName = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_service_request_main)
        setUpViewModel()

        intialiseViews()
        handleSubmitListner()
    }

    private fun handleSubmitListner() {
        dataBinding.submit.setOnClickListener {
            handlePostApi()
        }
    }

    private fun handlePostApi() {
        val jsonObject = JsonObject()
        jsonObject.addProperty("serviceProviderId", serviceProviderId)

        userId = Pref_storage.getDetail( this,"id").toInt()

        loginViewModel.postService(userId,jsonObject).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        handleNavigation()

                    }
                    Status.ERROR -> {
                        Log.e("error", "Error is captured")
                    }
                }

            }

        }
    }

    private fun handleNavigation() {
        var intent = Intent(this@ServiceRequestMain,ServiceRequestDetailScreen::class.java)
        startActivity(intent)
    }

    private fun setUpViewModel() {
        loginViewModel = ViewModelProvider(this, ViewModelFactory(ApiUtilis().getAPIServiceInKotlin(), this.application))[LoginViewModel::class.java]
    }

    private fun handleServiceTypeApi() {

        loginViewModel.getServiceType().observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        handleServiceType(it.data)

                    }
                    Status.ERROR -> {
                        Log.e("error", "Error is captured")
                    }
                }

            }

        }
    }

    private fun handleServiceType(data: ServiceTypeModel?) {
        var serviceTypeListName = ArrayList<String>()
        serviceTypeList.clear()
        serviceTypeListName.clear()
        if (data != null) {
            for (i in data.result.indices){
                serviceTypeList.add(data.result[i])
                serviceTypeListName.add(data.result[i].name)
            }
        }
        dataBinding.apply {
            val adapter = ArrayAdapter(this@ServiceRequestMain, R.layout.spinner_item, serviceTypeListName)
            spinnerServiceType.setAdapter(adapter)
            spinnerServiceType.setOnClickListener {
                spinnerServiceType.showDropDown()
            }
            spinnerServiceType.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //Do Nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //Do Nothing
                }
                override fun afterTextChanged(name: Editable?) {
                    enteredServiceTypeList = null
                    if (!name.isNullOrEmpty()){
                        enteredServiceTypeList = serviceTypeList.find { it.name.trim().toUpperCase().equals(name?.trim().toString().toUpperCase(), true) }

                        dataBinding.apply {
                            spinnerServices.setText("")
                            spinnerServiceProvider.setText("")
                            serviceList.clear()
                            enteredServiceList = null

                            serviceProviderList.clear()
                            enteredServiceProviderList=null
                        }
                        if (enteredServiceTypeList!=null){
                            serviceTypeId = enteredServiceTypeList!!.id
                            serviceTypeName = enteredServiceTypeList!!.name
                            handleServicesApi()
                        }

                    }


                }

            })


        }


    }

    private fun handleServicesApi() {
        loginViewModel.getServices(serviceTypeId).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        handleServices(it.data)

                    }
                    Status.ERROR -> {
                        Log.e("error", "Error is captured")
                    }
                }

            }

        }
    }

    override fun onBackPressed() {
        return
    }


    private fun handleServices(data: ServiceModel?) {
        var serviceNameList = ArrayList<String>()
        serviceList.clear()
        serviceNameList.clear()
        if (data != null) {
            for (i in data.result.indices){
                serviceList.add(data.result[i])
                serviceNameList.add(data.result[i].name)
            }
        }
        dataBinding.apply {
            val adapter = ArrayAdapter(this@ServiceRequestMain, R.layout.spinner_item, serviceNameList)
            spinnerServices.setAdapter(adapter)
            spinnerServices.setOnClickListener {
                spinnerServices.showDropDown()
            }
            spinnerServices.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //Do Nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //Do Nothing
                }
                override fun afterTextChanged(name: Editable?) {
                    enteredServiceList = null
                    if (!name.isNullOrEmpty()){
                        enteredServiceList = serviceList.find { it.name.trim().toUpperCase().equals(name?.trim().toString().toUpperCase(), true) }
                        dataBinding.apply {
                            spinnerServiceProvider.setText("")
                            serviceProviderList.clear()
                            enteredServiceProviderList=null
                        }
                        if (enteredServiceTypeList!=null){

                            serviceId = enteredServiceList!!.id
                            serviceName = enteredServiceList!!.name
                            handleServiceProviderList()
                        }
                    }


                }

            })


        }

    }

    private fun handleServiceProviderList() {
        loginViewModel.getServicesProvider(serviceId).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        handleServiceProvider(it.data)

                    }
                    Status.ERROR -> {
                        Log.e("error", "Error is captured")
                    }
                }

            }

        }
    }

    private fun handleServiceProvider(data: ServiceTypeModel?) {
        var serviceProviderNameList = ArrayList<String>()

        serviceProviderList.clear()
        serviceProviderNameList.clear()

        if (data != null) {
            for (i in data.result.indices){
                serviceProviderList.add(data.result[i])
                serviceProviderNameList.add(data.result[i].name)
            }
        }
        dataBinding.apply {
            val adapter = ArrayAdapter(this@ServiceRequestMain, R.layout.spinner_item, serviceProviderNameList)
            spinnerServiceProvider.setAdapter(adapter)
            spinnerServiceProvider.setOnClickListener {
                spinnerServiceProvider.showDropDown()
            }
            spinnerServiceProvider.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //Do Nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //Do Nothing
                }
                override fun afterTextChanged(name: Editable?) {
                    enteredServiceProviderList = null

                    if (!name.isNullOrEmpty()){
                        enteredServiceProviderList = serviceProviderList.find { it.name.trim().toUpperCase().equals(name?.trim().toString().toUpperCase(), true) }

                        if (enteredServiceProviderList!=null){
                            serviceProviderId = enteredServiceProviderList!!.id
                            serviceProviderName = enteredServiceProviderList!!.name
                            handleSubmitBtn()
                        }
                    }
                }

            })


        }
    }

    private fun handleSubmitBtn() {
        dataBinding.submit.isEnabled = true
    }


    private fun intialiseViews() {

          userName= Pref_storage.getDetail( this,"displayName")

        dataBinding.apply {
            actionbarAccount.apply {
                cartImg.visibility = View.GONE
                searchTxt.visibility = View.GONE
                displayNameTxt.text = userName
            }
        }
        handleServiceTypeApi()

    }
}