package com.tolganacar.androidvotingsystem.view.loginpage

import android.content.Context
import android.content.SharedPreferences
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
import com.tolganacar.androidvotingsystem.databinding.FragmentLoginPageBinding
import kotlinx.android.synthetic.main.fragment_login_page.*

class LoginPageFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var viewBinding: FragmentLoginPageBinding
    private val db = Firebase.firestore
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentLoginPageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        loginRegisterButtonOnClickListener()
    }

    private fun loginRegisterButtonOnClickListener() {
        buttonLoginLoginPage.setOnClickListener {
            val tcNo = viewBinding.editTextTcNoLogin.text.toString()
            editor.putString("tcNo", tcNo).apply()
            val password = viewBinding.editTextPasswordLogin.text.toString()

            if(tcNo.equals("") || password.equals("")) {
                Toast.makeText(this@LoginPageFragment.requireActivity(),"Enter email and password.",Toast.LENGTH_LONG).show()
            } else {
                if(tcNo.equals("00000000000") && password.equals("admin123")) {
                    val action = LoginPageFragmentDirections.actionLoginPageFragmentToResultPageFragment()
                    findNavController().navigate(action)
                } else {
                    val usersRef = db.collection("users")
                    usersRef
                        .whereEqualTo("tcNo", tcNo)
                        .whereEqualTo("password", password)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            if (!querySnapshot.isEmpty) {
                                val action = LoginPageFragmentDirections.actionLoginPageFragmentToVoteFragment()
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(this@LoginPageFragment.requireActivity(),"Failed to login.",Toast.LENGTH_LONG).show()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@LoginPageFragment.requireActivity(),it.localizedMessage,Toast.LENGTH_LONG).show()
                        }
                }
            }
        }

        buttonRegisterLoginPage.setOnClickListener {
            val action = LoginPageFragmentDirections.actionLoginPageFragmentToRegisterPageFragment()
            findNavController().navigate(action)
        }
    }

}