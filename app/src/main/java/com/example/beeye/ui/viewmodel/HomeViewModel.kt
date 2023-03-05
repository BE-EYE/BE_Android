package com.example.beeye.ui.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.beeye.data.repository.TesseractRepositoryImpl
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class HomeViewModel(): ViewModel() {
    private val ocrRepositoryImpl = TesseractRepositoryImpl()
    private lateinit var fragmentActivity: FragmentActivity
    private var language: String = ""
    private val dataPath: String get() =
        fragmentActivity.filesDir.toString() + "/"

    fun setFragmentActivity(activity: FragmentActivity) {
        fragmentActivity = activity
    }

    fun setTesseract() {
        ocrRepositoryImpl.setTess(dataPath, "kor+eng")
    }

    fun checkFile(lang: String) {
        val dir = File(dataPath+"tessdata/")
        if (!dir.exists() && dir.mkdirs()) {
            copyFile(lang)
            language += lang
        }

        if (dir.exists()) {
            var dataFilePath = dataPath+"tessdata/"+lang+".traineddata"
            var dataFile = File(dataFilePath)
            if(!dataFile.exists()) {
                copyFile(lang)
                language += lang
            }
        }
    }

    private fun copyFile(lang: String){
        try {
            val filePath = dataPath+"tessdata/"+lang+".traineddata"
            val assetManager = fragmentActivity.assets

            val inputStream = assetManager.open("tess_data/"+lang+".traineddata")
            val outputStream = FileOutputStream(filePath)

            var buffer = ByteArray(1024)

            var read = inputStream.read(buffer)
            while (read != -1) {
                outputStream.write(buffer, 0, read)
                read = inputStream.read(buffer)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        }catch(e : FileNotFoundException){
            Log.v("error",e.toString())
        }catch (e : IOException)
        {
            Log.v("error",e.toString())
        }
    }

    fun getOcrText(bitmap: Bitmap): String? = ocrRepositoryImpl.getOCRText(bitmap)
}