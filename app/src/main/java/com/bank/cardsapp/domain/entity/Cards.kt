package com.bank.cardsapp.domain.entity

data class Cards(
    val id: Int,
    val uid: String,
    val number: String,
    val expiryDate: String,
    val type: String,
    val iconResId: Int
)