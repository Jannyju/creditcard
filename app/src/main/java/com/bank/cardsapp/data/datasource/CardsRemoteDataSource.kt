package com.bank.cardsapp.data.datasource

import com.bank.cardsapp.data.model.CardsResponse

interface CardsRemoteDataSource {
    suspend fun getCards(
        pageNumber: Int
    ): List<CardsResponse>
}
