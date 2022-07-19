package com.peoplelink.inapisdk

interface ActionCallBack {
    fun onLoading(isLoading : Boolean)
    fun onSuccess(url: String?)
    fun onFailure(error: String?)
}