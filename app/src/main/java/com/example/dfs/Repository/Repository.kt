package com.example.dfs.Repository

import com.example.dfs.Api.APIInterface
import com.google.gson.JsonObject

class Repository (var apiInterface: APIInterface) {

    suspend fun postLogin(jsonObject: JsonObject) = apiInterface.postLogin(jsonObject)
    suspend fun getUserServiceRequest(userId:Int) = apiInterface.getUserServiceRequest(userId)

    suspend fun getServiceType() = apiInterface.getServiceType()
    suspend fun getService(serviceTypeId:Int) = apiInterface.getServices(serviceTypeId)
    suspend fun getServicesProvider(serviceId:Int) = apiInterface.getServicesProvider(serviceId)


    suspend fun postService(userId:Int,jsonObject: JsonObject) = apiInterface.PostService(userId,jsonObject)
    suspend fun getItemsList(userId:Int) = apiInterface.getItemsList(userId)
    suspend fun postAddToCart(userId:Int,jsonObject: JsonObject) = apiInterface.postAddToCart(userId,jsonObject)
    suspend fun getCartItems(userId:Int) = apiInterface.getItemCart(userId)
    suspend fun deleteItem(userId:Int,itemId:Int) = apiInterface.deleteItem(userId,itemId)


}