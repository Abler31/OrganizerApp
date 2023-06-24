package com.my.org.data.retrofit

data class Data(
    val columns: List<String>,
    val `data`: List<List<Any>>,
    val index: List<String>
)