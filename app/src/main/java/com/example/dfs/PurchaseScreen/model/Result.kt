package com.example.dfs.PurchaseScreen.model

data class Result(
    val amount: Int,
    val cartCount: Int,
    val description: String,
    val id: Int,
    val itemName: String,
    var internalCount: Int = 0,

)