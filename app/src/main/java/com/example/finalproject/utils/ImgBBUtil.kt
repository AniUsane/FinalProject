package com.example.finalproject.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

object ImageUtils{
    fun prepareImage(uri: Uri, context: Context): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open input stream for URI: $uri")

        val fileBytes = inputStream.readBytes()
        inputStream.close()

        val fileName = "profile_image_${System.currentTimeMillis()}.jpg"
        val requestFile = fileBytes.toRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("image", fileName, requestFile)
    }
}