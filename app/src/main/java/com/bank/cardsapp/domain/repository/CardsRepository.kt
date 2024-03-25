package com.bank.cardsapp.domain.repository

import androidx.paging.PagingData
import com.bank.cardsapp.domain.entity.Cards
import kotlinx.coroutines.flow.Flow


interface CardsRepository {
    suspend fun getCards(): Flow<PagingData<Cards>>
}