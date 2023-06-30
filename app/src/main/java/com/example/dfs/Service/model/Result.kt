package com.example.dfs.Service.model

data class Result(
    val createdDate: String,
    val id: Int,
    val serialNo: String,
    val serviceDescription: String,
    val serviceName: String,
    val serviceProviderName: String,
    val serviceTypeName: String,
    val status: String
)