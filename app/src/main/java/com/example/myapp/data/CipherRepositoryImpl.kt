package com.example.myapp.data

import com.example.myapp.domain.CipherRepository
import javax.inject.Inject

class CipherRepositoryImpl @Inject constructor() : CipherRepository {

    override fun generateAlphabet(lang: Enum<LangEnum>): List<Char> {
        var alphabet = mutableListOf<Char>()
        when (lang) {
            LangEnum.ENGLISH -> {
                alphabet = ('A'..'Z').toMutableList()
            }

            LangEnum.RUSSIAN -> {
                alphabet = ('А'..'Я').toMutableList()
                alphabet.add(6, 'Ё')
            }

            else -> {
                throw RuntimeException("Unknown language")
            }
        }
        return alphabet
    }

    override fun decrypt(cipherModel: CipherModel): String {
        val alphabet = generateAlphabet(cipherModel.lang)

        var res = ""

        for (i in cipherModel.text.uppercase().toList()) {
            res += if (i != ' ') {
                if (alphabet.indexOf(i) < cipherModel.shift) {
                    alphabet.last() + alphabet.indexOf(i) - cipherModel.shift + 1
                } else {
                    alphabet[alphabet.indexOf(i) - cipherModel.shift]
                }
            } else {
                ' '
            }
        }
        return res.lowercase()
    }

    override fun encrypt(cipherModel: CipherModel): String {
        val alphabet = generateAlphabet(cipherModel.lang)
        var res = ""
        val lastCharPosition = alphabet.indexOf(alphabet.last()) + 1
        for (i in cipherModel.text.uppercase().toList()) {
            res += if (i != ' ') {
                if (alphabet.indexOf(i) + cipherModel.shift > lastCharPosition) {
                    alphabet.first() + (alphabet.indexOf(i) + cipherModel.shift - lastCharPosition)
                } else {
                    alphabet[alphabet.indexOf(i) + cipherModel.shift]
                }
            } else {
                ' '
            }
        }
        return res.lowercase()
    }

}