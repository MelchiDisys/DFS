package com.example.dfs.LoginScreen.model

data class LoginModel(
    val errors: List<String>,
    val message: String,
    val result: Result,
    val statusCode: Int,
    val statusDescription: String
)