package com.rawtooth.dailynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rawtooth.dailynotes.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

lateinit var resgisterBinding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        resgisterBinding=FragmentRegisterBinding.inflate(inflater,container,false)

        return resgisterBinding.root
    }


}