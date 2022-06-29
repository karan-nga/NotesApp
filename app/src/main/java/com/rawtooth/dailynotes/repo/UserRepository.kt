package com.rawtooth.dailynotes.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rawtooth.dailynotes.api.UserApi
import com.rawtooth.dailynotes.models.UserRequest
import com.rawtooth.dailynotes.models.UserResponseRegister
import com.rawtooth.dailynotes.utils.NetworksHandling
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI:UserApi){

      private var _userResponseLiveData=MutableLiveData<NetworksHandling<UserResponseRegister>>()
     val userResponsLiveData:LiveData<NetworksHandling<UserResponseRegister>>
     get() = _userResponseLiveData

     suspend fun registerUser(userRequest: UserRequest){
          _userResponseLiveData.postValue(NetworksHandling.Loading())
          val response=userAPI.signup(userRequest)
          handleResponse(response)

     }

     private fun handleResponse(response: Response<UserResponseRegister>) {
          if (response.isSuccessful && response.body() != null) {
               _userResponseLiveData.postValue(NetworksHandling.Success(response.body()!!))
          } else if (response.errorBody() != null) {
               val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
               _userResponseLiveData.postValue(NetworksHandling.Error(errorObj.getString("message")))
          } else {
               _userResponseLiveData.postValue(NetworksHandling.Error("Something went wrong"))
          }
     }

     suspend fun  loginUser(userRequest: UserRequest){
          _userResponseLiveData.postValue(NetworksHandling.Loading( ))
          val response=userAPI.signin(userRequest)
          handleResponse(response)
          Log.d("code",response.body().toString())
     }
}