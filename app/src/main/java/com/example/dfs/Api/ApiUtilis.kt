package com.example.dfs.Api

class ApiUtilis {

    var BASE_URL = "https://dfsprototype.azurewebsites.net"

    fun getAPIServiceInKotlin(): APIInterface {
        return RetrofitClient().getClient(BASE_URL)!!.create(APIInterface::class.java)
    }
}