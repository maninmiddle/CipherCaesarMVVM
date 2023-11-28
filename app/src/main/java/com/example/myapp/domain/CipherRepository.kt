package com.example.myapp.domain

import com.example.myapp.data.CipherModel
import com.example.myapp.data.LangEnum

interface CipherRepository {
    fun decrypt(cipherModel: CipherModel): String

    fun encrypt(cipherModel: CipherModel): String

    fun generateAlphabet(lang: Enum<LangEnum>): List<Char>
}