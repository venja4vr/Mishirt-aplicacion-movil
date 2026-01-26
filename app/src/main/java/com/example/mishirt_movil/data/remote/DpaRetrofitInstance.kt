package com.example.mishirt_movil.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DpaRetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/juanbrujo/0fd2f4d126b3ce5a95a7dd1f28b3d8dd/raw/b8575eb82dce974fd2647f46819a7568278396bd/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: DpaApiService by lazy {
        retrofit.create(DpaApiService::class.java)
    }
}
