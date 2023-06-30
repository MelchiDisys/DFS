package com.example.dfs.PurchaseScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dfs.Api.ApiUtilis
import com.example.dfs.LoginScreen.viewModel.LoginViewModel
import com.example.dfs.PurchaseScreen.model.ItemModel
import com.example.dfs.PurchaseScreen.model.Result
import com.example.dfs.R
import com.example.dfs.databinding.ActivityPurchaseBinding
import com.example.dfs.preferences.Pref_storage
import com.example.dfs.utils.Status
import com.example.dfs.utils.ViewModelFactory
import com.google.gson.JsonObject

class PurchaseActivity : AppCompatActivity(),PurchaseAdapter.OnItemClicked {
    private lateinit var dataBinding: ActivityPurchaseBinding
    lateinit var adapter: PurchaseAdapter
    private lateinit var loginViewModel: LoginViewModel
    private var userId: Int = 0
    var userName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)
        setUpViewModel()
        intializeViews()
        handleGetItem()
    }

    private fun setUpViewModel() {
        loginViewModel = ViewModelProvider(this, ViewModelFactory(ApiUtilis().getAPIServiceInKotlin(), this.application))[LoginViewModel::class.java]
    }

    private fun handleGetItem() {
        loginViewModel.getItemsList(userId).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        if (it.data!=null){
                            handleRecyclerView(it.data)
                        }

                    }
                    Status.ERROR -> {
                        Log.e("error", "Error is captured")
                    }
                }

            }

        }
    }



    private fun intializeViews() {
        userId = Pref_storage.getDetail( this,"id").toInt()
        userName= Pref_storage.getDetail( this,"displayName")

        dataBinding.apply {
            actionbarAccount.displayNameTxt.text = userName
            actionbarAccount.cartImg.setOnClickListener {
                val intent = Intent(this@PurchaseActivity,YourCartActivity::class.java)
                startActivityForResult(intent, 100)

            }

        }
    }

    private fun handleRecyclerView(purchaseList:ItemModel) {

            val layoutManager = LinearLayoutManager(this)
            dataBinding.apply {
                listView.layoutManager = layoutManager
                adapter = PurchaseAdapter(this@PurchaseActivity, purchaseList.result,this@PurchaseActivity)
                listView.adapter = adapter
            } }

    override fun addToCart(result: Result, count: String) {

        if (result!=null){
            handlePostAddToCart(result,count)
        }

    }

    override fun deleteItem(result: Result) {
        handleDeleteApi(result)
    }


    fun handleDeleteApi(result: Result) {
        var itemId = result.id

        loginViewModel.deleteItem(userId, itemId).observe(
            this
        ) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        handleGetItem()
                    }
                    Status.ERROR -> {
                        Log.e("error", "Error is captured")
                    }
                }

            }

        }
    }



    fun handlePostAddToCart(result: Result, count: String) {
        var itemId = result.id

        val jsonObject = JsonObject()
        jsonObject.addProperty("itemId", itemId)
        jsonObject.addProperty("count", count.toInt())

        loginViewModel.postAddToCart(userId,jsonObject).observe(this,
            {
                it.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            dataBinding.progressBar.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            dataBinding.progressBar.visibility = View.GONE
                            handleGetItem()

                        }
                        Status.ERROR -> {
                            Log.e("error","Error is captured")
                        }
                    }

                }

            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
         if (resultCode == 200){
             handleGetItem()
         }
    }


}