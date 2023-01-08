package com.lafa.di

import com.lafa.common.Constant
import com.lafa.network.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {


    @Singleton
    @Provides
    fun providesBaseUrl(): String {
        return Constant.BASE_URL
    }


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(providesBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient
                .Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build())
            .build()
    }


    @Singleton
    @Provides
    fun getAPI(): ApiService {
        return provideRetrofit().create(ApiService::class.java)
    }

}