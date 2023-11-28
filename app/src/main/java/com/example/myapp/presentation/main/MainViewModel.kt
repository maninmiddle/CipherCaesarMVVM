package com.example.myapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.CipherModel
import com.example.myapp.data.CipherRepositoryImpl
import com.example.myapp.domain.CipherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CipherRepository
) : ViewModel() {
    private val _textStateFlow = MutableStateFlow<String>("")
    val textStateFlow: StateFlow<String>
        get() = _textStateFlow

    fun encryptText(cipherModel: CipherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _textStateFlow.value = repository.encrypt(cipherModel)
        }
    }

    fun decryptText(cipherModel: CipherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _textStateFlow.value = repository.decrypt(cipherModel)
        }
    }


}