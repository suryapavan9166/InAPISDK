package com.peoplelink.inapisdksample

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.peoplelink.inapisdk.ActionCallBack
import com.peoplelink.inapisdk.InApiSDK

class MainActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)

        InApiSDK().getUrl(
            "62c2d064468f48722e5b4af8",
            "suryapavan@peoplelinkvc.com", object : ActionCallBack {
                override fun onLoading(isLoading: Boolean) {
                    Log.d("SURYA", "onLoading: $isLoading")
                    if(isLoading)
                        progressBar!!.visibility = View.VISIBLE
                    else
                        progressBar!!.visibility = View.GONE
                }


                override fun onSuccess(message: String?) {
                    Log.d("SURYA", "onSuccess: " + message.toString())
                }

                override fun onFailure(error: String?) {
                    Log.d("SURYA", "onFailure: " + error.toString())
                }

            }
        )
    }
}