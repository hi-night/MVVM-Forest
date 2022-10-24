package com.example.forest.ui.main.hoem

import androidx.paging.*
import com.example.forest.entity.ArticleEntity
import com.example.forest.mvvm.base.repository.BaseRepositoryRemote
import com.example.forest.mvvm.base.repository.IRemoteDataSource
import com.example.forest.mvvm.http.service.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepository @Inject constructor(
    remoteDataSource: HomeRemoteDataSource,
    private val api: UserService
) :
    BaseRepositoryRemote<HomeRemoteDataSource>(remoteDataSource) {

    private val config = PagingConfig(
        // 每页数量
        pageSize = 20,
        // 初始化加载数量，默认为 pageSize * 3
        initialLoadSize = 1,
        // 预刷新的距离，距离最后一个 item 多远时加载数据
        prefetchDistance = 3,
        // 开启占位符
        enablePlaceholders = true,
    )

    fun getArticleList(): Flow<PagingData<ArticleEntity>> {
        return Pager(config) {
            HomeRemoteDataSource(api)
        }.flow.map {
            it
        }
    }
}

class HomeRemoteDataSource @Inject constructor(
    private val api: UserService
) : PagingSource<Int, ArticleEntity>(), IRemoteDataSource {

    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? {
        // 调用 refresh 时 从第几页请求 返回 null 从第一页开始
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        return try {
            // 未定义时加载第 1 页
            val nextPageNumber = params.key ?: 1
            val response = api.getArticleList(nextPageNumber.toString())

            LoadResult.Page(
                data = response.data?.datas!!,
                prevKey = null, // 仅向后翻页
                nextKey = response.data.curPage
            )
        } catch (e: Exception) {
            // 在此块中处理错误
            LoadResult.Error(e)
        }
    }
}