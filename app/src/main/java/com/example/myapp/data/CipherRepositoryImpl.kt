package com.example.myapp.data

import com.example.myapp.domain.CipherRepository
import javax.inject.Inject

class CipherRepositoryImpl @Inject constructor() : CipherRepository {

    private val regexIgnoreSymbols = """[^A-Za-z0-9]""".toRegex()

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
            res += if (regexIgnoreSymbols.matchEntire(i.toString()) == null) {
                if (alphabet.indexOf(i) < cipherModel.shift) {
                    alphabet.last() + alphabet.indexOf(i) - cipherModel.shift + 1
                } else {
                    alphabet[alphabet.indexOf(i) - cipherModel.shift]
                }
            } else {
                i
            }
        }
        return res.lowercase()
    }

    override fun encrypt(cipherModel: CipherModel): String {
        val alphabet = generateAlphabet(cipherModel.lang)
        var res = ""
        val lastCharPosition = alphabet.indexOf(alphabet.last()) + 1
        for (i in cipherModel.text.uppercase().toList()) {
            res += if (regexIgnoreSymbols.matchEntire(i.toString()) == null) {
                if (alphabet.indexOf(i) + cipherModel.shift > lastCharPosition) {
                    alphabet.first() + (alphabet.indexOf(i) + cipherModel.shift - lastCharPosition)
                } else {
                    alphabet[alphabet.indexOf(i) + cipherModel.shift]
                }
            } else {
                i
            }
        }
        return res.lowercase()
    }

}