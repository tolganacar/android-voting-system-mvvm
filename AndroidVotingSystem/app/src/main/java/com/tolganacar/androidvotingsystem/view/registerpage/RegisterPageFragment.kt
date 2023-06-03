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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tolganacar.androidvotingsystem.databinding.FragmentRegisterPageBinding
import kotlinx.android.synthetic.main.fragment_register_page.*

class RegisterPageFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var viewBindingRegister: FragmentRegisterPageBinding
    private val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBindingRegister = FragmentRegisterPageBinding.inflate(inflater, container, false)
        return viewBindingRegister.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerUser()
    }

    private fun registerUser() {
        buttonRegisterRegisterPage.setOnClickListener {
            val password = viewBindingRegister.editTextPassword.text.toString()
            val tcNo = viewBindingRegister.editTextTcNo.text.toString()
            val fullName = viewBindingRegister.editTextFullName.text.toString()

            if (password.equals("") || tcNo.length != 11  || tcNo.equals("") || fullName.equals("")) {
                Toast.makeText(
                    this@RegisterPageFragment.requireActivity(),
                    "Please enter your information completely.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val user = HashMap<String, Any>()
                user["fullName"] = fullName
                user["tcNo"] = tcNo
                user["password"] = password

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        val action =
                            RegisterPageFragmentDirections.actionRegisterPageFragmentToLoginPageFragment()
                        findNavController().navigate(action)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this@RegisterPageFragment.requireActivity(),
                            it.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }

        buttonLoginRegisterPage.setOnClickListener {
            val action =
                RegisterPageFragmentDirections.actionRegisterPageFragmentToLoginPageFragment()
            findNavController().navigate(action)
        }
    }
}
