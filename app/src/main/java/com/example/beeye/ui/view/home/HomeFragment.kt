package com.example.beeye.ui.view.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.beeye.databinding.FragmentHomeBinding
import java.io.IOException

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMainCamera.setOnClickListener {
            requestCameraLauncher.launch(REQUIRED_CAMERA_PERMISSION)
        }

        binding.btnMainGallery.setOnClickListener {
            requestGalleryLauncher.launch(REQUIRED_GALLERY_PERMISSION)
        }
    }

    private val requestGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            getImageLauncher.launch("image/*")
        } else {
            val dialog = HomePermissionDialog()
            dialog.show( requireActivity().supportFragmentManager,"HomePermissionDialog")
        }
    }

    private val requestCameraLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){ isGranted ->
        if (isGranted) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            imageCaptureLauncher.launch(intent)
        }else {
            val dialog = HomePermissionDialog()
            dialog.show( requireActivity().supportFragmentManager,"HomePermissionDialog")
        }
    }

    private val imageCaptureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            //deprecated 처리 필요
            val bitmap = it.data?.extras?.get("data")
            Log.d("bitmap", bitmap.toString())
            //요약 페이지로 전환 필요
        }
    }

    private val getImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()) { uri ->
            uri.let {
                try {
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val source = ImageDecoder.createSource(requireActivity().contentResolver, uri!!)
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                    }

                    //bitmap 처리
                    Log.d("bitmap", bitmap.toString())
                }catch (e: IOException) {
                    Log.e("버전 에러", e.toString())
                }
            }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val REQUIRED_CAMERA_PERMISSION = android.Manifest.permission.CAMERA
        private const val REQUIRED_GALLERY_PERMISSION = android.Manifest.permission.READ_MEDIA_IMAGES
    }
}