package com.bank.cardsapp.domain.usecase

import androidx.paging.PagingData
import com.bank.cardsapp.domain.entity.Cards
import com.bank.cardsapp.domain.repository.CardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetCardsUseCase @Inject constructor(
    private val repository: CardsRepository
) : BaseUseCase<Unit, Flow<PagingData<Cards>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<Cards>> {
        return repository.getCards()
    }
}