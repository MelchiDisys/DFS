package com.example.dfs.Service.model

data class ServiceTypeModel(
    val errors: List<String>,
    val message: String,
    val result: ArrayList<ResultX>,
    val statusCode: Int,
    val statusDescription: String
)