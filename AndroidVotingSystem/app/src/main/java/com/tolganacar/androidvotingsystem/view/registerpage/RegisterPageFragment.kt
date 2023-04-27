package com.tolganacar.androidvotingsystem.view.registerpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tolganacar.androidvotingsystem.R
import com.tolganacar.androidvotingsystem.view.mainpage.MainPageFragmentDirections
import kotlinx.android.synthetic.main.fragment_register_page.*

class RegisterPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLoginButtonOnClickListener()
    }

    private fun registerLoginButtonOnClickListener() {
        buttonRegisterRegisterPage.setOnClickListener {

        }

        buttonLoginRegisterPage.setOnClickListener {
            val action = RegisterPageFragmentDirections.actionRegisterPageFragmentToLoginPageFragment()
            findNavController().navigate(action)
        }
    }

}