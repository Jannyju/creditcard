package com.bank.cardsapp.domain.usecase

import androidx.paging.PagingData
import com.bank.cardsapp.domain.entity.Cards
import com.bank.cardsapp.domain.repository.CardsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertNotNull


@ExperimentalCoroutinesApi
class GetCardsUseCaseTest {

    // Mock the repository
    private val mockCardsRepository: CardsRepository = mockk()

    // Subject under test
    private lateinit var getCardsUseCase: GetCardsUseCase

    @Before
    fun setUp() {
        // Initialize the use case with the mocked repository
        getCardsUseCase = GetCardsUseCase(mockCardsRepository)
    }

    @Test
    fun `getCards invokes repository and returns expected data`() = runBlockingTest {
        // Define expected output
        val expectedOutput: Flow<PagingData<Cards>> = flowOf(PagingData.empty())

        // Set behavior for the mocked repository
        coEvery { mockCardsRepository.getCards() } returns expectedOutput

        // Execute the use case
        val result = getCardsUseCase.execute(Unit).first()

        // Assert that the result matches the expected output
        assertEquals(expectedOutput.first(), result)
    }

    @Test
    fun `when getCards is called, then repository should return a flow of PagingData of Cards`() = runBlockingTest {
        // Given
        val mockPagingData: PagingData<Cards> = PagingData.from(listOf(Cards(id = 8851, uid = "e2c26521-929d-49a3-bc17-d5541e5e13a2", number = "1212-1221-1121-1234", expiryDate = "2027-03-25", type = "Visa", iconResId = 1)))
        val expectedFlow: Flow<PagingData<Cards>> = flow { emit(mockPagingData) }

        coEvery { mockCardsRepository.getCards() } returns expectedFlow

        // When
        val result = getCardsUseCase.execute(Unit)

        // Then
        assertNotNull("The flow of PagingData<Cards> should not be null", result.first())
    }
}
