package com.bank.cardsapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bank.cardsapp.domain.entity.Cards
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FakeListViewModel(private val fakeCardsFlow: Flow<PagingData<Cards>>) : ViewModel() {
    private val _cardsState = MutableStateFlow<PagingData<Cards>>(PagingData.empty())
    val cardsState: StateFlow<PagingData<Cards>> = _cardsState.asStateFlow()

    init {
        viewModelScope.launch {
            fakeCardsFlow.collect {
                _cardsState.value = it
            }
        }
    }
}
