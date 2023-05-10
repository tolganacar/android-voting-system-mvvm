package com.tolganacar.androidvotingsystem.view.loginpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tolganacar.androidvotingsystem.R
import kotlinx.android.synthetic.main.fragment_login_page.*
import kotlinx.android.synthetic.main.fragment_register_page.*

class LoginPageFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_page, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginRegisterButtonOnClickListener()
    }

    private fun loginRegisterButtonOnClickListener() {

        buttonLoginLoginPage.setOnClickListener {
            val email = editTextEmailLogin.text.toString()
            val password = editTextPasswordLogin.text.toString()

            if(email.equals("") || password.equals("")) {
                Toast.makeText(this@LoginPageFragment.requireActivity(),"Enter email and password.",Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    val action = LoginPageFragmentDirections.actionLoginPageFragmentToVoteFragment()
                    findNavController().navigate(action)
                }.addOnFailureListener {
                    Toast.makeText(this@LoginPageFragment.requireActivity(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }

        buttonRegisterLoginPage.setOnClickListener {
            val action = LoginPageFragmentDirections.actionLoginPageFragmentToRegisterPageFragment()
            findNavController().navigate(action)
        }
    }

}