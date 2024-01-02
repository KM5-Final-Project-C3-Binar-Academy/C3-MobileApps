package com.c3.mobileapps.utils

import android.util.Patterns

object ValidasiHelper {
    fun isValidInput(input: String): Boolean {
        return if (Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            // Input adalah alamat email
            true
        } else {
            // Input bukan alamat email, coba cocokkan sebagai nomor telepon
            input.matches(Regex("^(0|62|\\+62)\\d+$"))
        }
    }


    fun checkCharacter(number: String): String {
        // Mengecek apakah karakter pertama adalah "0" atau "62"
        val regex = Regex("^(0|62)")
        val matchResult = regex.find(number)

        var modifiedText = number

        // Jika ada kecocokan dan karakter pertama adalah "0", hapus karakter pertama
        matchResult?.let {
            if (it.groupValues[1] == "0") {
                modifiedText = number.substring(it.range.last + 1)
            } else if (it.groupValues[1] == "62") {
                // Jika karakter pertama adalah "62", hapus karakter pertama
                modifiedText = number.substring(it.range.last + 1)
            }
        }

        // Menambahkan "+62" di awal jika belum ada
        if (!modifiedText.startsWith("+62")) {
            modifiedText = "+62$modifiedText"
        }

        return modifiedText
    }
}