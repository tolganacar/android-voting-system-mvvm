package com.tolganacar.androidvotingsystem.view.fingerprintauthpage

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tolganacar.androidvotingsystem.databinding.FragmentVoteBinding
import com.tolganacar.androidvotingsystem.view.loginpage.LoginPageFragmentDirections
import java.util.concurrent.Executor

class FingerprintAuthFragment : Fragment() {

    private lateinit var binding: FragmentVoteBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewFingerprint.setOnClickListener {
            checkDeviceHasBiometric()
            biometricPrompt.authenticate(promptInfo)
        }

        executor = ContextCompat.getMainExecutor(this@FingerprintAuthFragment.requireActivity())
        biometricPrompt = BiometricPrompt(
            this@FingerprintAuthFragment.requireActivity(),
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        this@FingerprintAuthFragment.requireActivity(),
                        "Authentication error: $errString",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        this@FingerprintAuthFragment.requireActivity(),
                        "Authentication succeeded!",
                        Toast.LENGTH_LONG
                    ).show()

                    val action = FingerprintAuthFragmentDirections.actionVoteFragmentToFaceIdFragment()
                    findNavController().navigate(action)

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        this@FingerprintAuthFragment.requireActivity(),
                        "Authentication failed.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verify Biometric")
            .setSubtitle("Confirm your fingerprint to continue")
            .setNegativeButtonText("Cancel")
            .build()

    }

    private fun checkDeviceHasBiometric() {
        val biometricManager = BiometricManager.from(this@FingerprintAuthFragment.requireActivity())
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                binding.textViewClickHere.text = "App can authenticate using biometrics."
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d("MY_APP_TAG", "Biometric features are currently unavailable.")
                binding.textViewClickHere.text = "Biometric features are currently unavailable."
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }

                startActivityForResult(enrollIntent, 100)
            }
        }
    }
}