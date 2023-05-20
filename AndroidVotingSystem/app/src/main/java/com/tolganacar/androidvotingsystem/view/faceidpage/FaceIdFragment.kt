package com.tolganacar.androidvotingsystem.view.faceidpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.tolganacar.androidvotingsystem.databinding.FragmentFaceIdBinding

class FaceIdFragment : Fragment() {

    private lateinit var viewBinding: FragmentFaceIdBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var faceDetector: FaceDetector

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFaceIdBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()
        setupCamera()
        setupFaceDetector()
    }

    private fun setupCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
            }
            val imageAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(cameraExecutor, FaceAnalyzer())
            }
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
            } catch (exc: Exception) {
                exc.localizedMessage
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun setupFaceDetector() {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        faceDetector = FaceDetection.getClient(options)
    }

    inner class FaceAnalyzer : ImageAnalysis.Analyzer {
        @SuppressLint("UnsafeOptInUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image ?: return
            val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            faceDetector.process(inputImage)
                .addOnSuccessListener { faces ->
                    for (face in faces) {
                        viewBinding.buttonDetectFaces.setOnClickListener {
                            val action = FaceIdFragmentDirections.actionFaceIdFragmentToVoteFragment()
                            findNavController().navigate(action)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}