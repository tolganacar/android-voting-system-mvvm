package com.tolganacar.androidvotingsystem.view.resultpage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tolganacar.androidvotingsystem.R
import com.tolganacar.androidvotingsystem.databinding.FragmentLoginPageBinding
import com.tolganacar.androidvotingsystem.databinding.FragmentResultPageBinding

class ResultPageFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var viewBinding: FragmentResultPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentResultPageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countVotesForCandidate("Recep Tayyip Erdogan",viewBinding.textViewErdoganVoteResult)
        countVotesForCandidate("Kemal Kilicdaroglu",viewBinding.textViewKilicdarogluVoteResult)
        countVotesForCandidate("Muharrem Ince",viewBinding.textViewInceVoteResult)
        countVotesForCandidate("Sinan Ogan",viewBinding.textViewOganVoteResult)
    }

    private fun countVotesForCandidate(candidate: String, textView: TextView) {
        db.collection("votes")
            .document(candidate)
            .collection("voters")
            .get()
            .addOnSuccessListener { result ->
                val voteCount = result.size()
                textView.text = "$voteCount oy"
            }
            .addOnFailureListener { e ->
                textView.text = e.localizedMessage
            }
    }


}