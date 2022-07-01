package com.rawtooth.dailynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rawtooth.dailynotes.databinding.FragmentLoginBinding
import com.rawtooth.dailynotes.models.UserRequest
import com.rawtooth.dailynotes.utils.NetworksHandling
import com.rawtooth.dailynotes.utils.TokenManager
import com.rawtooth.dailynotes.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding?=null
    private val  binding get()=_binding!!
    private val authViewMode by viewModels<AuthViewModel>()
    //token
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            _binding= FragmentLoginBinding.inflate(inflater,container,false)
        if(tokenManager.getToken()!=null){
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val validateResult=validateInput()
            if(validateResult.first){
                authViewMode.loginUser(getUserRequest())
            }
           else{
               binding.txtError.text=validateResult.second
            }
        }
        binding.btnSignUp.setOnClickListener {
        findNavController().popBackStack()
        }
        bindObserver()
    }

    private fun bindObserver() {
        authViewMode.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible=false
            when(it){
                is NetworksHandling.Success ->{
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworksHandling.Error ->{
                    binding.txtError.text=it.message
                }
                is NetworksHandling.Loading ->{
                    binding.progressBar.isVisible=true
                }
            }
        })
    }

    private fun getUserRequest(): UserRequest {
        val email=binding.txtEmail.text.toString()
        val password=binding.txtPassword.text.toString()
        return UserRequest("",email,password)
    }

    private fun validateInput(): Pair<Boolean,String> {
            val userRequest=getUserRequest()
        return  authViewMode.validateCredentials(userRequest.username,userRequest.email,userRequest.password,true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}