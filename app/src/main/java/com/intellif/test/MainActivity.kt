package com.intellif.test

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.intellif.test.dagger.A
import com.intellif.test.dagger.B
import com.intellif.test.dagger.DaggerMainComponent
import com.intellif.test.dagger.MainComponent
import com.intellif.test.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.LinkedHashMap

@Route(path = "/test/mainActivity")
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var a: A

    @Named("dev")
    @Inject
    lateinit var devB: B

    @Named("proDuct")
    @Inject
    lateinit var productB: B


    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val linkedList = LinkedList<Int>()
        linkedList.last
        linkedList.first

        ARouter.init(application)

        ARouter.getInstance().build("")
            .navigation()


        val linkedHashMap = LinkedHashMap<String, String>()
        linkedHashMap.entries
        DaggerMainComponent.builder().build().inject(this)
    }

    fun interface sfsf {
        fun a()
    }

    override fun onResume() {
        super.onResume()
        var handler = Handler()
        val tv = findViewById<TextView>(R.id.tv)
        tv.handler
        Looper.getMainLooper()
//        var linkedList = LinkedList<String>()
//        linkedList.peek()

        val into = Glide.with(this)
            .load("")
            .into(mBinding.img)

    }

    val client = OkHttpClient.Builder()
        //            .addInterceptor()
        .build()

//    @Throws(IOException::class)

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