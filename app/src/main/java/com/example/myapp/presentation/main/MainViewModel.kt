package com.example.myapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.data.CipherModel
import com.example.myapp.data.CipherRepositoryImpl
import com.example.myapp.domain.CipherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CipherRepository
) : ViewModel() {
    private val _textLiveData = MutableLiveData<String>()
    val textLiveData: LiveData<String>
        get() = _textLiveData

    fun encryptText(cipherModel: CipherModel) {
        _textLiveData.postValue(repository.encrypt(cipherModel))
    }

    fun decryptText(cipherModel: CipherModel) {
        _textLiveData.postValue(repository.decrypt(cipherModel))
    }


}