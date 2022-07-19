package com.peoplelink.inapisdk

import com.peoplelink.inapisdk.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET("getTokenbyId")
    fun getToken(@Query("projectId") projectId : String): Call<GetTokenResponse?>?

    @POST("createSession")
    fun sessionIdData(@Body sessionRequest: SessionRequest?): Call<SessionResponse?>?

    @GET("getTemplateDataById")
    fun templateData(@Query("projectId") projectId : String): Call<TemplateResponse?>?
}