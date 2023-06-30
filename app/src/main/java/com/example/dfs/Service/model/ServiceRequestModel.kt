package com.example.dfs.Service.model

data class ServiceRequestModel(
    val errors: List<String>,
    val message: String,
    val result: List<Result>,
    val statusCode: Int,
    val statusDescription: String
)