package com.example.dfs.PurchaseScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dfs.Api.ApiUtilis
import com.example.dfs.LoginScreen.viewModel.LoginViewModel
import com.example.dfs.PurchaseScreen.model.ItemModel
import com.example.dfs.PurchaseScreen.model.Result
import com.example.dfs.R
import com.example.dfs.databinding.CartScreenBinding
import com.example.dfs.preferences.Pref_storage
import com.example.dfs.utils.Status
import com.example.dfs.utils.ViewModelFactory
import com.google.gson.JsonObject

class YourCartActivity : AppCompatActivity(), CartAdpater.OnItemClicked {
    private lateinit var dataBinding: CartScreenBinding
    lateinit var adapter: CartAdpater
    private lateinit var loginViewModel: LoginViewModel
    private var userId: Int = 0
    var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.cart_screen)
        setUpViewModel()
        intializeViews()
        handleCartItems()
    }

    private fun setUpViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiUtilis().getAPIServiceInKotlin(), this.application)
        )[LoginViewModel::class.java]
    }

    private fun handleCartItems() {
        loginViewModel.getItemsCart(userId).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        dataBinding.progressBar.visibility = View.GONE
                        if (it.data != null) {
                            handleRecyclerView(it.data)
                        }else{

                            dataBinding.listView.visibility = View.GONE
                            dataBinding.textViewNoRecordFound2.visibility = View.VISIBLE

                        }

                    }
                    Status.ERROR -> {
                        dataBinding.progressBar.visibility = View.GONE
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
            actionbarAccount.backBtn.setOnClickListener {
                setResult(200)
                finish()
            }

        }
    }


    private fun handleRecyclerView(purchaseList: ItemModel) {
        dataBinding.listView.visibility = View.VISIBLE
        dataBinding.textViewNoRecordFound2.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)
        dataBinding.apply {
            listView.layoutManager = layoutManager
            adapter = CartAdpater(this@YourCartActivity, purchaseList.result, this@YourCartActivity)
            listView.adapter = adapter
        }
    }

    override fun deleteItem(result: Result) {

        if (result != null) {
            handleDeleteApi(result)
        }

    }

    override fun updateItem(result: Result, count: String) {
        handlePostAddToCart(result,count)
    }


    fun handlePostAddToCart(result: Result, count: String) {
        var itemId = result.id

        val jsonObject = JsonObject()
        jsonObject.addProperty("itemId", itemId)
        jsonObject.addProperty("count", count.toInt())

        loginViewModel.postAddToCart(userId, jsonObject).observe(this,
            {
                it.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            dataBinding.progressBar.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            dataBinding.progressBar.visibility = View.GONE
                            handleCartItems()

                        }
                        Status.ERROR -> {
                            Log.e("error", "Error is captured")
                        }
                    }

                }

            })
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
                        handleCartItems()
                    }
                    Status.ERROR -> {
                        Log.e("error", "Error is captured")
                    }
                }

            }

        }
    }


}