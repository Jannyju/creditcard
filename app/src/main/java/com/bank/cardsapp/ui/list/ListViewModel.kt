package com.bank.cardsapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bank.cardsapp.domain.entity.Cards
import com.bank.cardsapp.domain.usecase.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
) : ViewModel() {
    private val _cardsState: MutableStateFlow<PagingData<Cards>> = MutableStateFlow(value = PagingData.empty())
    val cardsState: MutableStateFlow<PagingData<Cards>> get() = _cardsState

    init {
        onEvent(ListEvent.GetHome)
    }

    fun onEvent(event: ListEvent) {
        viewModelScope.launch {
            when (event) {
                is ListEvent.GetHome -> {
                    getCards()
                }
            }
        }
    }

    private suspend fun getCards() {
        getCardsUseCase.execute(Unit)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _cardsState.value = it
            }
    }
}

sealed class ListEvent {
    object GetHome : ListEvent()
}
