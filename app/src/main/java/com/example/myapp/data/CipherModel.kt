package com.example.myapp.data

data class CipherModel(
    val lang: Enum<LangEnum>,
    val text: String,
    val shift: Int
)

enum class LangEnum {
    RUSSIAN,
    ENGLISH
}
