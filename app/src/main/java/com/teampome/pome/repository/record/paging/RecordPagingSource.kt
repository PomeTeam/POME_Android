package com.teampome.pome.repository.record.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.teampome.pome.model.RecordData
import com.teampome.pome.network.RecordService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException

class RecordPagingSource @AssistedInject constructor(
    private val service: RecordService,
    @Assisted("goalId") private val goalId: Int // Assisted는 Hilt를 통해 주입되지 않는다는 Annotation
) : PagingSource<Int, RecordData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecordData> {
        return try {
            val pageNumber = params.key ?: 0
            val pageSize = params.loadSize
            val response = service.getRecordByGoalId(goalId, pageNumber, pageSize)

            if (response.isSuccessful) {
                val baseAllData = response.body()?.data
                val items = baseAllData?.content?.filterNotNull() ?: emptyList()
                val prevKey = if (pageNumber == 0) null else pageNumber - 1
                val nextKey = if (items.isEmpty() || response.body()?.data?.empty == true) null else pageNumber + 1

                LoadResult.Page(items, prevKey, nextKey)
            } else {
                LoadResult.Error(Exception("Error loading data"))
            }
        } catch(e : IOException) {
            return LoadResult.Error(e)
        } catch(e : HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecordData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    @com.squareup.inject.assisted.AssistedInject.Factory
    interface Factory {
        fun create(goalId: Int): RecordPagingSource
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            goalId: Int
        ): () -> RecordPagingSource = {
            assistedFactory.create(goalId)
        }
    }
}