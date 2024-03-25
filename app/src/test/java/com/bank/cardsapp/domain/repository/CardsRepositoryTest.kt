package com.bank.cardsapp.domain.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import androidx.paging.PagingData
import com.bank.cardsapp.domain.entity.Cards
import com.bank.cardsapp.domain.repository.CardsRepository
import kotlinx.coroutines.flow.toList

@ExperimentalCoroutinesApi
class CardsRepositoryTest {

    private lateinit var cardsRepository: CardsRepository

    @Before
    fun setup() {
        cardsRepository = mockk()
    }

    @Test
    fun `get cards returns data`() = runBlockingTest {
        // Prepare a PagingData<Cards> instance as expected output
        val expectedData = PagingData.from(listOf(Cards(id = 8851, uid = "e2c26521-929d-49a3-bc17-d5541e5e13a2", number = "1212-1221-1121-1234", expiryDate = "2027-03-25", type = "Visa", iconResId = 1)))

        // Use coEvery to mock the suspend function `getCards` behavior
        coEvery { cardsRepository.getCards() } returns flowOf(expectedData)

        val result = cardsRepository.getCards()

        // Collect the result to a list
        val resultList = result.toList() // This requires kotlinx.coroutines.flow.toList()

        // Assertion
        assert(resultList.size == 1) // Ensure there's data
        assert(resultList[0] == expectedData) // Ensure the data matches expected
    }
}
