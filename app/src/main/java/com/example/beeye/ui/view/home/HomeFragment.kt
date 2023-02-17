package com.example.beeye.ui.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.beeye.R
import com.example.beeye.databinding.FragmentHomeBinding
import java.net.URL

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMainCamera.setOnClickListener {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
    }



    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){ isGranted ->
        if (isGranted) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            imageCaptureLauncher.launch(intent)
        }else {
            Toast.makeText(requireContext(), "해당 기능은 카메라 권한이 필요합니다!", Toast.LENGTH_SHORT).show()
        }
    }

    private val imageCaptureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val bitmap = it.data?.extras
            Log.d("bitmap", bitmap.toString())
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val REQUIRED_PERMISSIONS = android.Manifest.permission.CAMERA
    }
}