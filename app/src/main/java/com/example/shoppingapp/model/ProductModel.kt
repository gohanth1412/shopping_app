package com.example.shoppingapp.model

data class ProductModel (
    val idProduct: String? = "",
    val thumbnail: String? = "",
    val productName: String? = "",
    val productPrice: String? = "",
    val category: String? = "",
    val offer: Boolean? = false,
    val selling: Boolean? = false,
    val details: String? = ""
)