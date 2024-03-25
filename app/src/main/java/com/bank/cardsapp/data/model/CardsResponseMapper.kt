package com.bank.cardsapp.data.model

import com.bank.cardsapp.R
import com.bank.cardsapp.domain.entity.Cards
import kotlin.math.abs

fun CardsResponse.mapFromEntity() = Cards(
    id = this.id,
    uid = this.uid,
    number = this.number,
    expiryDate = this.expiryDate,
    type = this.type,
    iconResId = iconResources[abs(this.id.hashCode()) % iconResources.size]
)

fun List<CardsResponse>.mapFromListModel(): List<Cards> = this.map { it.mapFromEntity() }

val iconResources = listOf(
    R.drawable.icon01,
    R.drawable.icon02,
    R.drawable.icon03,
    R.drawable.icon04,
    R.drawable.icon05,
    R.drawable.icon06,
    R.drawable.icon07,
    R.drawable.icon08,
    R.drawable.icon09
)