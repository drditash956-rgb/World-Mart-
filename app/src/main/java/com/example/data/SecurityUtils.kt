package com.example.data

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest

object SecurityUtils {

    private const val DEFAULT_SALT = "wm_prod_sec_2026"

    fun hashPassword(password: String, salt: String = DEFAULT_SALT): String {
        val input = "$salt:$password"
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray(Charsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun verifyPassword(password: String, hash: String, salt: String = DEFAULT_SALT): Boolean {
        return hashPassword(password, salt) == hash
    }

    fun saveBytesToInternalStorage(
        context: Context,
        subDir: String,
        prefix: String,
        extension: String,
        bytes: ByteArray
    ): String {
        val dir = File(context.filesDir, subDir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, "${prefix}_${System.currentTimeMillis()}.$extension")
        FileOutputStream(file).use { out ->
            out.write(bytes)
            out.flush()
        }
        return file.absolutePath
    }

    fun copyUriToInternalStorage(
        context: Context,
        subDir: String,
        uri: Uri,
        fileNamePrefix: String = "upload"
    ): String? {
        return try {
            val dir = File(context.filesDir, subDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val extension = context.contentResolver.getType(uri)?.substringAfterLast("/") ?: "jpg"
            val targetFile = File(dir, "${fileNamePrefix}_${System.currentTimeMillis()}.$extension")
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(targetFile).use { output ->
                    input.copyTo(output)
                }
            }
            targetFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
