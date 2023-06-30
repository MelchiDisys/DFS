package com.example.dfs.Service.model

data class ServiceModel(
    val errors: List<String>,
    val message: String,
    val result: List<ResultXX>,
    val statusCode: Int,
    val statusDescription: String
)