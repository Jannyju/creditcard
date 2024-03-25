package com.bank.cardsapp.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bank.cardsapp.ui.util.app.Constants
import com.bank.cardsapp.data.datasource.CardsRemoteDataSource
import com.bank.cardsapp.data.model.mapFromListModel
import com.bank.cardsapp.domain.entity.Cards
import retrofit2.HttpException
import java.io.IOException

class CardsPagingSource(
    private val remoteDataSource: CardsRemoteDataSource,
) : PagingSource<Int, Cards>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cards> {
        return try {
            val currentPage = params.key ?: Constants.INITIAL_PAGE
            val cards = remoteDataSource.getCards(pageNumber = currentPage)
            val nextPageNumber = if (cards.isEmpty()) null else currentPage + 1
            val prevPageNumber = if (currentPage == Constants.INITIAL_PAGE) null else currentPage - 1
            LoadResult.Page(
                data = cards.mapFromListModel(),
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cards>): Int? {
        return state.anchorPosition
    }

}