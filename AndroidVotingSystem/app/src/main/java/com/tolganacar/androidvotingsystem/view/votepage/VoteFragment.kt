package com.tolganacar.androidvotingsystem.view.votepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tolganacar.androidvotingsystem.R
import com.tolganacar.androidvotingsystem.databinding.FragmentFingerprintAuthBinding
import com.tolganacar.androidvotingsystem.databinding.FragmentVoteBinding
import com.tolganacar.androidvotingsystem.view.registerpage.RegisterPageFragmentDirections
import kotlinx.android.synthetic.main.fragment_login_page.editTextTcNoLogin
import kotlinx.android.synthetic.main.fragment_register_page.editTextFullName
import kotlinx.android.synthetic.main.fragment_vote.buttonVoteErdogan
import kotlinx.android.synthetic.main.fragment_vote.buttonVoteInce
import kotlinx.android.synthetic.main.fragment_vote.buttonVoteKilicdaroglu
import kotlinx.android.synthetic.main.fragment_vote.buttonVoteOgan
import java.net.Inet4Address
import java.net.NetworkInterface

class VoteFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentVoteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonVoteErdogan.setOnClickListener {
            saveVote("Recep Tayyip Erdogan")
        }
        binding.buttonVoteKilicdaroglu.setOnClickListener {
            saveVote("Kemal Kilicdaroglu")
        }
        binding.buttonVoteInce.setOnClickListener {
            saveVote("Muharrem Ince")
        }
        binding.buttonVoteOgan.setOnClickListener {
            saveVote("Sinan Ogan")
        }

    }

    private fun saveToDatabase(candidate: String) {
        val chosen = "chosen"

        val voteData = hashMapOf(
            "vote" to chosen
        )

        db.collection("votes")
            .document(candidate)
            .collection("voters")
            .add(voteData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Vote recorded successfully!",Toast.LENGTH_LONG).show()
                val action =
                    VoteFragmentDirections.actionVoteFragmentToMainPageFragment2()
                findNavController().navigate(action)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
            }
    }

    private fun saveVote(candidate: String){
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Confirm your vote")
        alert.setMessage("Do you want to vote for $candidate?")
        alert.setPositiveButton("Yes") {dialog, which ->
            saveToDatabase(candidate)
        }
        alert.setNegativeButton("No"){dialog, which ->
            Toast.makeText(requireContext(),"You did not vote.",Toast.LENGTH_SHORT).show()
        }
        alert.show()
    }
}
