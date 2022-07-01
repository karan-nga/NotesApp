package com.rawtooth.dailynotes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rawtooth.dailynotes.databinding.FragmentRegisterBinding
import com.rawtooth.dailynotes.models.UserRequest
import com.rawtooth.dailynotes.utils.NetworksHandling
import com.rawtooth.dailynotes.utils.TokenManager
import com.rawtooth.dailynotes.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    lateinit var resgisterBinding: FragmentRegisterBinding
    private val authViewModel by viewModels<AuthViewModel>()
    //token
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resgisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        if(tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        return resgisterBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resgisterBinding.btnSignUp.setOnClickListener {
            val validateResult = validateUserInput()
            if (validateResult.first) {
                authViewModel.registerUser(getUserRequest())
            } else {
                resgisterBinding.txtError.text = validateResult.second
            }
        }
        resgisterBinding.btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        bindObserver()
    }

    private fun getUserRequest(): UserRequest {
        val userName = resgisterBinding.txtUsername.text.toString()
        val email = resgisterBinding.txtEmail.text.toString()
        val password = resgisterBinding.txtPassword.text.toString()
        Log.d("email",email)
        return UserRequest(userName, email, password)
    }

    fun validateUserInput(): Pair<Boolean, String> {
        val userInfo = getUserRequest()
        return authViewModel.validateCredentials(
            userInfo.username,
            userInfo.email,
            userInfo.password,
            false
        )
    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            resgisterBinding.progressBar.isVisible = false
            when (it) {
                is NetworksHandling.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworksHandling.Error -> {
                    resgisterBinding.txtError.text = it.message
                }
                is NetworksHandling.Loading -> {
                    resgisterBinding.progressBar.isVisible = true
                }
            }
        })
    }
}