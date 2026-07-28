package com.example.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ReceiptImagePicker {

    fun createImageUri(context: Context): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = File(context.cacheDir, "receipts").apply { mkdirs() }
        val imageFile = File.createTempFile(
            "MPESA_${timeStamp}_",
            ".jpg",
            storageDir
        )
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    }

    fun getFileNameFromUri(context: Context, uri: Uri): String {
        return uri.lastPathSegment ?: "receipt_${System.currentTimeMillis()}.jpg"
    }
}
