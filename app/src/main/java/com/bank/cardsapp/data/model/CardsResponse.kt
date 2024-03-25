package com.bank.cardsapp.data.model

import com.google.gson.annotations.SerializedName

data class CardsResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("uid")
    val uid: String,

    @SerializedName("credit_card_number")
    val number: String,

    @SerializedName("credit_card_expiry_date")
    val expiryDate: String,

    @SerializedName("credit_card_type")
    val type: String,

)