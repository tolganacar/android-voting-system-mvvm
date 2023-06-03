package com.tolganacar.androidvotingsystem.view.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tolganacar.androidvotingsystem.R
import com.tolganacar.androidvotingsystem.view.loginpage.LoginPageFragment
import kotlinx.android.synthetic.main.fragment_main_page.*

class MainPageFragment : Fragment() {

    //private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //auth = Firebase.auth

        //val currentUser = auth.currentUser

        /*if(currentUser != null){
            val action = MainPageFragmentDirections.actionMainPageFragmentToVoteFragment()
            findNavController().navigate(action)
        }*/
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