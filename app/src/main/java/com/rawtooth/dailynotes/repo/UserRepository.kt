package com.rawtooth.dailynotes.repo

import android.util.Log
import com.rawtooth.dailynotes.api.UserApi
import com.rawtooth.dailynotes.models.UserRequest
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI:UserApi){
     suspend fun registerUser(userRequest: UserRequest){
          val response=userAPI.signup(userRequest)
          Log.d("code",response.body().toString())
     }
     suspend fun  loginUser(userRequest: UserRequest){
          val response=userAPI.signin(userRequest)
          Log.d("code",response.body().toString())
     }
}