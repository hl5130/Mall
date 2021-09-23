package com.cqteam.baselibrary.data.net

import com.cqteam.baselibrary.common.BaseConstant
import com.cqteam.baselibrary.utils.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 10:39 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
class RetrofitFactory private constructor() {
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit
    private val interceptor: Interceptor = Interceptor { chain ->
        // 拿到请求体，添加 Header
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("charset", "utf-8")
            .addHeader(BaseConstant.KEY_SP_TOKEN,AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
            .build()
        chain.proceed(request)
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BaseConstant.SERVICE_ADDRESS)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(initOkHttpClient())
            .build()
    }


    private fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(initLogInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}