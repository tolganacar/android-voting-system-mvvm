package com.tolganacar.androidvotingsystem.view.registerpage

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
import kotlinx.android.synthetic.main.fragment_register_page.*

class RegisterPageFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_page, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerLoginButtonOnClickListener()
    }

    private fun registerLoginButtonOnClickListener() {


        buttonRegisterRegisterPage.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val tcNo = editTextTcNo.text.toString()
            val fullName = editTextFullName.text.toString()
            if (email.equals("") || password.equals("") || tcNo.equals("") || fullName.equals("")) {
                Toast.makeText(this@RegisterPageFragment.requireActivity(), "Please enter your information completely.", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    //success
                    val action = RegisterPageFragmentDirections.actionRegisterPageFragmentToLoginPageFragment()
                    findNavController().navigate(action)
                }.addOnFailureListener {
                    Toast.makeText(this@RegisterPageFragment.requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        buttonLoginRegisterPage.setOnClickListener {
            val action = RegisterPageFragmentDirections.actionRegisterPageFragmentToLoginPageFragment()
            findNavController().navigate(action)
        }
    }
}