package com.example.shoppingapp.model

data class CartModel (
    val idCart: String? = "",
    val idUser: String? = "",
    val idProduct: String? = "",
    val countProduct: Int? = 0,
    val singlePrice: String? = "",
    val totalPrice: String? = "",
    val productName: String? = "",
    val thumbnail: String? = ""
)