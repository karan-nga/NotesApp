package com.rawtooth.dailynotes.di

import com.rawtooth.dailynotes.api.UserApi
import com.rawtooth.dailynotes.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit():Retrofit{
        return  Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build()
    }
    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit):UserApi{
        return retrofit.create(UserApi::class.java)
    }
}