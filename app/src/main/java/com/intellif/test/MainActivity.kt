package com.intellif.test

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.intellif.test.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val linkedList = LinkedList<Int>()
        linkedList.last
        linkedList.first
    }

    override fun onResume() {
        super.onResume()
        var handler = Handler()
        val tv = findViewById<TextView>(R.id.tv)
        tv.handler
        Looper.getMainLooper()
//        var linkedList = LinkedList<String>()
//        linkedList.peek()


    }

    val client = OkHttpClient.Builder()
        //            .addInterceptor()
        .build()

    @Throws(IOException::class)
    fun run(url: String): String? {
        val client = OkHttpClient.Builder()

            .build()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        //异步
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }

        })
        //同步
        client.newCall(request).execute().use { response -> return response.body?.string() }

    }


}