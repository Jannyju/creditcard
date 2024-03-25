package com.bank.cardsapp.data.network

import com.bank.cardsapp.data.model.CardsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CardsApi {
    companion object {
        const val SERVER_URL = "https://random-data-api.com/"
    }

    @GET("api/v2/credit_cards?size=100")
    suspend fun getCards(
        @Query("page") pageNumber: Int
   ): List<CardsResponse>
}