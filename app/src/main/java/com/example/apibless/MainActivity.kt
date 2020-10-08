package com.example.apibless

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
val URL = "https://api.icndb.com/jokes/random"
    var okHttpClient: OkHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nextBtn.setOnClickListener {
            runOnUiThread{
                progressBar.visibility = View.VISIBLE
            }
            val request: Request = Request.Builder().url(URL).build()
            okHttpClient.newCall(request).enqueue(object : Callback, okhttp3.Callback {

                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    Log.e("Error", e.toString())
                }
                override fun onResponse(call: okhttp3.Call, response: Response) {
                    val joke = (JSONObject(response!!.body()!!
                        .string()).getJSONObject("value").get("joke")).toString()
                    runOnUiThread{
                        progressBar.visibility = View.GONE
                        jock.text = joke
                    }
                }
            })
        }
    }
}
