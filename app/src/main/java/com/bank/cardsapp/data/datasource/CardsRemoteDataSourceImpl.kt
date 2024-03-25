package com.bank.cardsapp.data.datasource

import com.bank.cardsapp.data.network.CardsApi
import com.bank.cardsapp.data.model.CardsResponse
import javax.inject.Inject

class CardsRemoteDataSourceImpl @Inject constructor(
    private val api: CardsApi
) : CardsRemoteDataSource {

    override suspend fun getCards(
        pageNumber: Int
    ): List<CardsResponse> {
        return api.getCards(pageNumber = pageNumber)
    }

}