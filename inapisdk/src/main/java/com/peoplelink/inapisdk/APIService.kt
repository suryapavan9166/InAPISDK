package com.peoplelink.inapisdk

import com.peoplelink.inapisdk.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {

    @POST("applicationresetkey")
    fun userProjectData(@Body projectDataRequest: ProjectDataRequest?): Call<ProjectDataResponse?>?

    @POST("tokenVerification")
    fun userVerifyData(@Body userVerifyRequest: UserVerifyRequest?): Call<UserVerifyResponse?>?

    @POST("createSession")
    fun sessionIdData(@Body sessionRequest: SessionRequest?): Call<SessionResponse?>?

    @GET("getTemplateDataById")
    fun templateData(@Query("projectId") projectId : String): Call<TemplateResponse?>?
}