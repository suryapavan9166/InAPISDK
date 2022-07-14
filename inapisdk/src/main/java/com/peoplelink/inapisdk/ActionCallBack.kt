package com.peoplelink.inapisdk

interface ActionCallBack {
    fun onLoading(isLoading : Boolean)
    fun onSuccess(message: String?)
    fun onFailure(error: String?)
}