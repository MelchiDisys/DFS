package com.example.dfs.Api

import com.example.dfs.LoginScreen.model.LoginModel
import com.example.dfs.PurchaseScreen.model.ItemModel
import com.example.dfs.Service.ServiceRequestDetailScreen
import com.example.dfs.Service.model.ServiceModel
import com.example.dfs.Service.model.ServiceRequestModel
import com.example.dfs.Service.model.ServiceTypeModel
import com.google.gson.JsonObject
import retrofit2.http.*

interface APIInterface {

    @POST("/api/account/login")
    suspend fun postLogin(@Body jsonData: JsonObject):LoginModel

    @GET("/api/service/get-userservice-list/{userId}")
    suspend fun getUserServiceRequest(@Path("userId") userId: Int):ServiceRequestModel

    @GET("/api/service/get-servicetype-list")
    suspend fun getServiceType():ServiceTypeModel

    @GET("/api/service/get-service-list")
    suspend fun getServices(@Query("serviceTypeId") serviceTypeId:Int):ServiceModel

    @GET("/api/service/get-serviceprovider-list")
    suspend fun getServicesProvider(@Query("serviceId") serviceId:Int):ServiceTypeModel

    @POST("/api/service/add-userservice/{userId}")
    suspend fun PostService(@Path("userId") userId: Int,@Body jsonData: JsonObject):Any

    @GET("/api/shopping/get-items-list/{userId}")
    suspend fun getItemsList(@Path("userId") userId: Int):ItemModel


    @POST("/api/shopping/add-itemcart/{userId}")
    suspend fun postAddToCart(@Path("userId") userId: Int,@Body jsonData: JsonObject):Any

    @GET("/api/shopping/get-itemcart-list/{userId}")
    suspend fun getItemCart(@Path("userId") userId: Int):ItemModel

    @DELETE("/api/shopping/delete-itemcart/{userId}/{itemId}")
    suspend fun deleteItem(@Path("userId") userId: Int,@Path("itemId") itemId: Int):Any



}