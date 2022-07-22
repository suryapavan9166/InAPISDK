package com.peoplelink.inapisdksample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.peoplelink.inapisdk.ActionCallBack
import com.peoplelink.inapisdk.InApiSDK
import com.peoplelink.inapisdk.model.MeetingDetails

class MainActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)

        InApiSDK().getUrl(
            "62b1adae3f4101b0b4baf3d7",
            "suryapavan@peoplelinkvc.com", "Q4V3QJfEaP39aSo",
            MeetingDetails(30,"suryapavan@peoplelinkvc.com","sdvjksdkjk-vds","mdsvhjds",99),object : ActionCallBack {
                override fun onLoading(isLoading: Boolean) {
                    if(isLoading)
                        progressBar!!.visibility = View.VISIBLE
                    else
                        progressBar!!.visibility = View.GONE
                }


                override fun onSuccess(url: String?) {
                    Log.d("SURYA", "onSuccess: "+url)
                }

                override fun onFailure(error: String?) {
                    Log.d("SURYA", "onFailure: " + error.toString())
                }

            }
        )
    }
}