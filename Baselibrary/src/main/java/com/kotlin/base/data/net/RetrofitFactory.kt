package com.kotlin.base.data.net

import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor() {
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }
    private val retrofit: Retrofit;
    private val interceptor: Interceptor;
        init {
            interceptor = Interceptor { chain ->
                var request = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .header("charset", "utf-8")
                    .header("token",AppPrefsUtils.getString(BaseConstant
                        .KEY_SP_TOKEN))
                    .build()
                chain.proceed(request)
            }
            retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(initClient())
                .build()
        }

        private fun initClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(initLogInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        private fun initLogInterceptor(): Interceptor {
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY;

            return interceptor;
        }

        fun <T> create(service: Class<T>): T {
            return retrofit.create(service)
        }

}