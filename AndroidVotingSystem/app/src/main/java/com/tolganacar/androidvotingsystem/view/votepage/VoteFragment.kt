package com.tolganacar.androidvotingsystem.view.votepage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tolganacar.androidvotingsystem.databinding.FragmentVoteBinding

class VoteFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentVoteBinding
    private lateinit var sharedPreferences: SharedPreferences
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
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonVoteErdogan.setOnClickListener {
            saveVoteAlert("Recep Tayyip Erdogan")
        }
        binding.buttonVoteKilicdaroglu.setOnClickListener {
            saveVoteAlert("Kemal Kilicdaroglu")
        }
        binding.buttonVoteInce.setOnClickListener {
            saveVoteAlert("Muharrem Ince")
        }
        binding.buttonVoteOgan.setOnClickListener {
            saveVoteAlert("Sinan Ogan")
        }

    }

    private fun checkAndSaveVote(candidate: String) {
        val tcNo = sharedPreferences.getString("tcNo", null)

        db.collection("votes")
            .document("Recep Tayyip Erdogan")
            .collection("voters")
            .whereEqualTo("tcNo", tcNo)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    db.collection("votes")
                        .document("Kemal Kilicdaroglu")
                        .collection("voters")
                        .whereEqualTo("tcNo", tcNo)
                        .get()
                        .addOnSuccessListener { querySnapshot1 ->
                            if (querySnapshot1.isEmpty) {
                                db.collection("votes")
                                    .document("Muharrem Ince")
                                    .collection("voters")
                                    .whereEqualTo("tcNo", tcNo)
                                    .get()
                                    .addOnSuccessListener { querySnapshot2 ->
                                        if (querySnapshot2.isEmpty) {
                                            db.collection("votes")
                                                .document("Sinan Ogan")
                                                .collection("voters")
                                                .whereEqualTo("tcNo", tcNo)
                                                .get()
                                                .addOnSuccessListener { querySnapshot3 ->
                                                    if (querySnapshot3.isEmpty) {
                                                        saveToDatabase(candidate)
                                                    } else {
                                                        Toast.makeText(
                                                            requireContext(),
                                                            "You have already voted.",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                }
                                                .addOnFailureListener { exception ->
                                                    Toast.makeText(
                                                        requireContext(),
                                                        exception.localizedMessage,
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                        } else {
                                            Toast.makeText(
                                                requireContext(),
                                                "You have already voted.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Toast.makeText(
                                            requireContext(),
                                            exception.localizedMessage,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "You have already voted.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(
                                requireContext(),
                                exception.localizedMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "You have already voted.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext(),
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun saveToDatabase(candidate: String) {
        val tcNo = sharedPreferences.getString("tcNo", null)

        val voteData = hashMapOf(
            "tcNo" to tcNo
        )

        db.collection("votes")
            .document(candidate)
            .collection("voters")
            .add(voteData)
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Vote recorded successfully!",
                    Toast.LENGTH_LONG
                ).show()
                val action = VoteFragmentDirections.actionVoteFragmentToMainPageFragment2()
                findNavController().navigate(action)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext(),
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun saveVoteAlert(candidate: String) {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Confirm your vote")
        alert.setMessage("Do you want to vote for $candidate?")
        alert.setPositiveButton("Yes") { dialog, which ->
            checkAndSaveVote(candidate)
        }
        alert.setNegativeButton("No") { dialog, which ->
            Toast.makeText(requireContext(), "You did not vote.", Toast.LENGTH_SHORT).show()
        }
        alert.show()
    }
}
