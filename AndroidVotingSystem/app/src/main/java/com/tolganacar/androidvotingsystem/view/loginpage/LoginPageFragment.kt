package com.tolganacar.androidvotingsystem.view.loginpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tolganacar.androidvotingsystem.R
import com.tolganacar.androidvotingsystem.view.registerpage.RegisterPageFragmentDirections
import kotlinx.android.synthetic.main.fragment_login_page.*

class LoginPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginRegisterButtonOnClickListener()
    }

    private fun loginRegisterButtonOnClickListener() {
        buttonRegisterLoginPage.setOnClickListener {
            val action = LoginPageFragmentDirections.actionLoginPageFragmentToRegisterPageFragment()
            findNavController().navigate(action)
        }

        buttonLoginLoginPage.setOnClickListener {
            val action = LoginPageFragmentDirections.actionLoginPageFragmentToVoteFragment()
            findNavController().navigate(action)
        }
    }

}