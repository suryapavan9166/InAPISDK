package com.peoplelink.inapisdk

import android.os.Build
import android.util.Base64.encodeToString
import com.google.gson.Gson
import com.peoplelink.inapisdk.model.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class InApiSDK {

    fun getUrl(projectId: String, hostEmail: String, appKey: String, meetingDetails: MeetingDetails, callBack: ActionCallBack) {
        if (projectId.isNotEmpty()) {
            if (hostEmail.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {


                    val client = OkHttpClient.Builder().addInterceptor { chain ->
                        val newRequest: Request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $appKey")
                            .build()
                        chain.proceed(newRequest)
                    }.build()

                    val retrofit: Retrofit = Retrofit.Builder()
                        .client(client)
                        .baseUrl(Utils().baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val apiInterface: APIService =
                        retrofit.create(APIService::class.java)


                    val retrofitAPI: APIService =
                        RetrofitHelper.getInstance().create(APIService::class.java)
                    callBack.onLoading(true)

                    apiInterface.getToken(projectId)!!
                        .enqueue(object : Callback<GetTokenResponse?> {
                            override fun onResponse(
                                call: Call<GetTokenResponse?>,
                                response: Response<GetTokenResponse?>
                            ) {
                                if (response.isSuccessful) {
                                    val token = response.body()!!.data.token


                                    val sessionModal = SessionRequest(
                                        projectId,
                                        "sessionName",
                                        token,
                                        System.currentTimeMillis().toInt(),
                                        meetingDetails
                                    )
                                    retrofitAPI.sessionIdData(sessionModal)!!
                                        .enqueue(object :
                                            Callback<SessionResponse?> {
                                            override fun onResponse(
                                                call: Call<SessionResponse?>,
                                                response: Response<SessionResponse?>
                                            ) {
                                                if (response.isSuccessful) {
                                                    val encodedString: String =
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            Base64.getEncoder()
                                                                .encodeToString(
                                                                    hostEmail.toByteArray()
                                                                )
                                                        } else {
                                                            encodeToString(
                                                                hostEmail.toByteArray(),
                                                                0
                                                            )
                                                        }.toString()

                                                    retrofitAPI.templateData(
                                                        projectId
                                                    )!!.enqueue(object :
                                                        Callback<TemplateResponse?> {
                                                        override fun onResponse(
                                                            call: Call<TemplateResponse?>,
                                                            response: Response<TemplateResponse?>
                                                        ) {
                                                            if (response.isSuccessful) {
                                                                callBack.onLoading(
                                                                    false
                                                                )
                                                                val url =
                                                                    "https://" + response.body()!!.data.subDomain + ".invc.vc/" + response.body()!!.data.templateId + "?token=" + token + "&projectId=" + response.body()!!.data.projectId + "&uid=" + encodedString
                                                                callBack.onSuccess(
                                                                    url
                                                                )
                                                            } else {
                                                                val gson = Gson()
                                                                val (error, status) = gson.fromJson(
                                                                    response.errorBody()!!
                                                                        .string(),
                                                                    ErrorResponse::class.java
                                                                )
                                                                callBack.onLoading(
                                                                    false
                                                                )
                                                                callBack.onFailure(
                                                                    error
                                                                )
                                                            }
                                                        }

                                                        override fun onFailure(
                                                            call: Call<TemplateResponse?>,
                                                            t: Throwable
                                                        ) {
                                                            callBack.onLoading(false)
                                                            callBack.onFailure(t.message.toString())
                                                        }

                                                    })

                                                } else {
                                                    val gson = Gson()
                                                    val (error, status) = gson.fromJson(
                                                        response.errorBody()!!
                                                            .string(),
                                                        ErrorResponse::class.java
                                                    )
                                                    callBack.onLoading(false)
                                                    callBack.onFailure(error)
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<SessionResponse?>,
                                                t: Throwable
                                            ) {
                                                callBack.onLoading(false)
                                                callBack.onFailure(t.message)
                                            }

                                        })


                                } else {
                                    val gson = Gson()
                                    val (error, status) = gson.fromJson(
                                        response.errorBody()!!.string(),
                                        ErrorResponse::class.java
                                    )
                                    callBack.onLoading(false)
                                    callBack.onFailure(error)
                                }
                            }

                            override fun onFailure(call: Call<GetTokenResponse?>, t: Throwable) {
                                callBack.onLoading(false)
                                callBack.onFailure(t.message)
                            }

                        })
                }
            } else
                callBack.onFailure("Please host email id")
        } else
            callBack.onFailure("Please enter project id")
    }
}