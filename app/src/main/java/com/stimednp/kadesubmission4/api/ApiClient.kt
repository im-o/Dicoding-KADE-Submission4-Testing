package com.stimednp.kadesubmission4.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.stimednp.kadesubmission4.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by rivaldy on 11/10/2019.
 */

object ApiClient {
    val gson = GsonBuilder().setLenient().create()
    private val interceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }
    //for logging
    //val interceptor = HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

    private val tsdbClient = OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .build()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(tsdbClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val iServiceTsdb: IServiceTsdb = retrofit()
        .create(IServiceTsdb::class.java)
}