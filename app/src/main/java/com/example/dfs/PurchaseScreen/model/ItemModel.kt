package com.example.dfs.PurchaseScreen.model

data class ItemModel(
    val errors: List<String>,
    val message: String,
    val result: List<Result>,
    val statusCode: Int,
    val statusDescription: String
)