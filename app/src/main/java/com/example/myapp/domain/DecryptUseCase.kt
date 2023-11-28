package com.example.myapp.domain

import com.example.myapp.data.CipherModel
import javax.inject.Inject

class DecryptUseCase @Inject constructor(
    private val repository: CipherRepository,
    private val cipherModel: CipherModel
) {
    operator fun invoke() = repository.decrypt(cipherModel)
}