package com.bank.cardsapp.domain.di

import com.bank.cardsapp.data.network.CardsApi
import com.bank.cardsapp.data.datasource.CardsRemoteDataSource
import com.bank.cardsapp.data.datasource.CardsRemoteDataSourceImpl
import com.bank.cardsapp.data.repository.CardsRepositoryImpl
import com.bank.cardsapp.domain.repository.CardsRepository
import com.bank.cardsapp.domain.usecase.GetCardsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesCardsRemoteDataSource(
        api: CardsApi
    ): CardsRemoteDataSource {
        return CardsRemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun providesCardsRepository(
        cardsRemoteDataSource: CardsRemoteDataSource
    ): CardsRepository {
        return CardsRepositoryImpl(cardsRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesgetCardsUseCase(
        cardsRepository: CardsRepository
    ): GetCardsUseCase {
        return GetCardsUseCase(cardsRepository)
    }
}