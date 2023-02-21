package com.example.beeye.data.repository

import android.graphics.Bitmap
import com.example.beeye.common.ocr.Tesseract

class TesseractRepositoryImpl: TesseractRepository {
    private val tess = Tesseract.tess

    override fun getOCRText(bitmap: Bitmap): String? {
        tess.setImage(bitmap)
        return tess.utF8Text
    }

    override fun setTess(dataPath: String, lang: String) {
        tess.init(dataPath, lang)
    }
}