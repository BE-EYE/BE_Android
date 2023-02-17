package com.example.beeye.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beeye.R
import com.example.beeye.common.base.BaseDialog
import com.example.beeye.databinding.FragmentHomePermissionDialogBinding

class HomePermissionDialog : BaseDialog<FragmentHomePermissionDialogBinding>(R.layout.fragment_home_permission_dialog) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.dialogCameraBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun getViewBinding() = FragmentHomePermissionDialogBinding.inflate(layoutInflater)

}