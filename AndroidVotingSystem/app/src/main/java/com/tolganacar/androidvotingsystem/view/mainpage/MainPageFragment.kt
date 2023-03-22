package com.tolganacar.androidvotingsystem.view.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tolganacar.androidvotingsystem.R
import com.tolganacar.androidvotingsystem.view.loginpage.LoginPageFragment
import kotlinx.android.synthetic.main.fragment_main_page.*

class MainPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginRegisterOnClickListener()
    }

    private fun loginRegisterOnClickListener(){
        buttonLogin.setOnClickListener {
            val action = MainPageFragmentDirections.actionMainPageFragmentToLoginPageFragment()
            findNavController().navigate(action)
        }

        buttonRegister.setOnClickListener {
            val action = MainPageFragmentDirections.actionMainPageFragmentToRegisterPageFragment()
            findNavController().navigate(action)
        }
    }

}