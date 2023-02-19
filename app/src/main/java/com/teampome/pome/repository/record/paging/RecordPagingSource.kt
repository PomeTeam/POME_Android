//package com.teampome.pome.repository.record.paging
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.teampome.pome.model.RecordData
//import com.teampome.pome.model.base.BasePomeResponse
//import com.teampome.pome.network.RecordService
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//const val DEFAULT_PAGE_INDEX = 1
//
//class RecordPagingSource @Inject constructor(
//    private val service: RecordService,
//    private val userId: String
//) : PagingSource<Int, BasePomeResponse<RecordData>>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BasePomeResponse<RecordData>> {
//        val page = params.key ?: DEFAULT_PAGE_INDEX
//
//        return try {
//            val response = service.getRecordDataByUserId(userId, page, params.loadSize).body()
//
//            LoadResult.Page(
//                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
//                nextKey = if (response.isEmpty()) null else page + 1
//            )
//        } catch(e : IOException) {
//            return LoadResult.Error(e)
//        } catch(e : HttpException) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, BasePomeResponse<RecordData>>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//                state.closestItemToPosition(anchorPosition)?.
//        }
//    }
//}