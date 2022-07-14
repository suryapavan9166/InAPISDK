package com.peoplelink.inapisdk

import android.os.Build
import android.util.Base64.encodeToString
import android.util.Log
import com.google.gson.Gson
import com.peoplelink.inapisdk.model.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class InApiSDK {

    fun getUrl(projectId: String, hostEmail: String, callBack: ActionCallBack) {
        if (projectId.isNotEmpty()) {
            if (hostEmail.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.IO) {
                    val retrofitAPI: APIService =
                        RetrofitHelper.getInstance().create(APIService::class.java)
                    val modal = ProjectDataRequest(projectId)
                    callBack.onLoading(true)

                    retrofitAPI.userProjectData(modal)!!
                        .enqueue(object : Callback<ProjectDataResponse?> {
                            override fun onResponse(
                                call: Call<ProjectDataResponse?>,
                                response: Response<ProjectDataResponse?>
                            ) {
                                if (response.isSuccessful) {
                                    val token = response.body()!!.data.authToken

                                    val userModal = UserVerifyRequest(projectId, token)

                                    retrofitAPI.userVerifyData(userModal)!!
                                        .enqueue(object : Callback<UserVerifyResponse?> {
                                            override fun onResponse(
                                                call: Call<UserVerifyResponse?>,
                                                response: Response<UserVerifyResponse?>
                                            ) {
                                                if (response.isSuccessful) {
                                                    val sessionModal = SessionRequest(
                                                        projectId,
                                                        "sessionName",
                                                        token,
                                                        System.currentTimeMillis().toInt(),
                                                        MeetingDetails(
                                                            10,
                                                            hostEmail,
                                                            "ba24334d-la3j-sdede-a2343-b12345",
                                                            "session",
                                                            100
                                                        )
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

                                            override fun onFailure(
                                                call: Call<UserVerifyResponse?>,
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

                            override fun onFailure(call: Call<ProjectDataResponse?>, t: Throwable) {
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