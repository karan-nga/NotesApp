package com.rawtooth.dailynotes.api

import com.rawtooth.dailynotes.models.UserRequest
import com.rawtooth.dailynotes.models.UserResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/signup")
    suspend fun signup(@Body userRequest: UserRequest):Response<UserResponseRegister>
    @POST("/user/signin")
    suspend fun signin(@Body userRequest: UserRequest):Response<UserResponseRegister>

}