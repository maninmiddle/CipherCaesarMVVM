package com.example.myapp.domain

import com.example.myapp.data.CipherModel

class GenerateAlphabetUseCase(
    private val repository: CipherRepository,
    private val cipherModel: CipherModel
) {
    operator fun invoke() = repository.generateAlphabet(cipherModel.lang)
}