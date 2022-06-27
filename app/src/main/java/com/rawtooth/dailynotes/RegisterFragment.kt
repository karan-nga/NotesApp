package com.rawtooth.dailynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rawtooth.dailynotes.databinding.FragmentRegisterBinding
import com.rawtooth.dailynotes.models.UserRequest
import com.rawtooth.dailynotes.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

lateinit var resgisterBinding: FragmentRegisterBinding
private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        resgisterBinding=FragmentRegisterBinding.inflate(inflater,container,false)
        resgisterBinding.btnLogin.setOnClickListener{
             authViewModel.loginUser(UserRequest("Android","android@gmail.com","12"))
//            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        resgisterBinding.btnSignUp.setOnClickListener {
            authViewModel.registerUser(UserRequest("Android","android@gmail.com","12"))
//            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }
        return resgisterBinding.root
    }


}