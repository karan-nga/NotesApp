package com.rawtooth.dailynotes.viewModel

import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rawtooth.dailynotes.models.UserRequest
import com.rawtooth.dailynotes.models.UserResponseRegister
import com.rawtooth.dailynotes.repo.UserRepository
import com.rawtooth.dailynotes.utils.NetworksHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) :ViewModel(){

 val userResponseLiveData:LiveData<NetworksHandling<UserResponseRegister>>
 get() = userRepository.userResponsLiveData
    fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }
    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }
    fun validateCredentials(username:String,email:String,password:String,isLogin:Boolean): Pair<Boolean,String>{
        var result=Pair(true,"")
        if((!isLogin&&TextUtils.isEmpty(username))||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            result=Pair(false,"PLease provides all details")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result=Pair(false,"Please provides valid email")
        }
        else if(password.length<=5){
            result=Pair(false,"Password Length must be greater than 5")
        }
        return  result
    }
}
