package com.bank.cardsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bank.cardsapp.ui.util.app.Constants
import com.bank.cardsapp.data.datasource.CardsRemoteDataSource
import com.bank.cardsapp.data.repository.paging.CardsPagingSource
import com.bank.cardsapp.domain.entity.Cards
import com.bank.cardsapp.domain.repository.CardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val remoteDataSource: CardsRemoteDataSource
) : CardsRepository {

    override suspend fun getCards(): Flow<PagingData<Cards>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                CardsPagingSource(remoteDataSource)
            }
        ).flow
    }
}