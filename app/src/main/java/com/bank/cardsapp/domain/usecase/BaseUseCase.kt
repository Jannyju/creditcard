package com.bank.cardsapp.domain.usecase

interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}