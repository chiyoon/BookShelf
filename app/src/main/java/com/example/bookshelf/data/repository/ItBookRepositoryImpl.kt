package com.example.bookshelf.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookshelf.data.datasource.ItBookDataSource
import com.example.bookshelf.data.db.ItBookDetailEntity
import com.example.bookshelf.data.db.ItBookEntity
import com.example.bookshelf.data.dto.ApiException
import com.example.bookshelf.data.dto.GetBooksResponseDTO
import com.example.bookshelf.data.dto.GetNewResponseDTO
import com.example.bookshelf.data.dto.GetSearchResponseDTO
import com.example.bookshelf.domain.entity.GetBooksRequestEntity
import com.example.bookshelf.domain.entity.GetBooksResponseEntity
import com.example.bookshelf.domain.entity.GetNewResponseEntity
import com.example.bookshelf.domain.entity.GetSearchRequestEntity
import com.example.bookshelf.domain.entity.GetSearchResponseEntity
import com.example.bookshelf.domain.entity.UpdateBookMemoRequestEntity
import com.example.bookshelf.domain.respository.ItBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItBookRepositoryImpl @Inject constructor(
    private val itBookRemoteDataSource: ItBookDataSource.Remote,
    private val itBookLocalDataSource: ItBookDataSource.Local
) : ItBookRepository {

    override fun getNew(): Flow<Result<GetNewResponseEntity>> {
        return flow {
            itBookRemoteDataSource.getNew().collect { dto ->
                if (dto.isSuccess) {
                    dto.getOrNull()?.let {
                        itBookLocalDataSource.insertItBooks(it.books.toDBEntity())
                    }
                }

                emit(dto.mapCatching { it.toEntity() })
            }
        }
    }

    override fun getBooks(requestEntity: GetBooksRequestEntity): Flow<Result<GetBooksResponseEntity>> {
        return flow {
            // TODO: Local 과 Remote 의 데이터가 합쳐지는 패턴에서는 어떻게 작업하면 좋을까?
            val localBook = itBookLocalDataSource.getItBook(isbn13 = requestEntity.isbn13)
            val localBookDetail =
                itBookLocalDataSource.getItBookDetail(isbn13 = requestEntity.isbn13)

            itBookRemoteDataSource.getBooks(requestEntity.isbn13).collect { dto ->
                try {
                    val remoteData = dto.getOrThrow()
                    val memo = localBookDetail?.memo ?: ""

                    if (localBookDetail == null) {
                        itBookLocalDataSource.insertItBookDetail(remoteData.toDBEntity())
                    }

                    emit(dto.mapCatching { it.toEntity(memo) })
                } catch (e: ApiException) {
                    emit(Result.failure(ApiException(-1)))
                } catch (e: Exception) {
                    if (localBook == null || localBookDetail == null) {
                        emit(Result.failure(ApiException(-1)))
                    } else {
                        emit(Result.success(cacheToEntity(localBook, localBookDetail)))
                    }
                }
            }
        }
    }

    // TODO: Paging Data 에 Result 객체를 전달하여, 중간 데이터에서 오류가 난 경우 Job 을 중단하는 등의 에러 처리를 어떻게 구현하면 좋을지 고민
    override fun getSearch(request: GetSearchRequestEntity): Flow<PagingData<GetSearchResponseEntity.Book>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchPagingSource(
                    request.query,
                    itBookRemoteDataSource,
                    itBookLocalDataSource
                )
            }
        ).flow
    }

    override fun updateBookMemo(request: UpdateBookMemoRequestEntity): Flow<Result<Unit>> {
        return flow {
            emit(itBookLocalDataSource.updateBookMemo(request.isbn13, request.memo))
        }
    }

    // TODO: Mapper 를 Class 형태로 빼서 주입받아서 사용하는게 좋을까?
    private fun List<GetNewResponseDTO.Book>.toDBEntity() = this.map {
        ItBookEntity(
            it.isbn13, it.title, it.subtitle, it.image, it.url, it.price
        )
    }

    private fun GetBooksResponseDTO.toDBEntity() = ItBookDetailEntity(
        this.isbn13,
        this.isbn10,
        this.rating,
        this.pages,
        this.year,
        this.authors,
        this.publisher,
        this.language,
        this.desc,
        this.pdf
    )

    private fun cacheToEntity(localBook: ItBookEntity, localBookDetail: ItBookDetailEntity) =
        GetBooksResponseEntity(
            0,
            localBook.title,
            localBook.subTitle,
            localBookDetail.author,
            localBookDetail.publisher,
            localBookDetail.language,
            localBookDetail.isbn10,
            localBookDetail.isbn13,
            localBookDetail.pages,
            localBookDetail.year,
            localBookDetail.rating,
            localBookDetail.description,
            localBook.price,
            localBook.imageUrl,
            localBook.url,
            localBookDetail.pdf,
            localBookDetail.memo
        )

}

class SearchPagingSource(
    private val query: String,
    private val remoteDatasource: ItBookDataSource.Remote,
    private val localDatasource: ItBookDataSource.Local
) : PagingSource<Int, GetSearchResponseEntity.Book>() {

    override fun getRefreshKey(state: PagingState<Int, GetSearchResponseEntity.Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetSearchResponseEntity.Book> {
        val page = params.key ?: 1
        val result = remoteDatasource.getSearch(query, page)

        try {
            val data = result.getOrThrow()

            localDatasource.insertItBooks(data.books.toDBEntity())

            return LoadResult.Page(
                data = data.books.map { it.toEntity() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.books.isEmpty()) null else page + 1
            )
        } catch (e: ApiException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            // TODO: 좀 더 예쁘게 캐싱된 결과를 가져올 방법은?
            // 현재는 네트워크 오류시 local 에서 페이징을 요청하는 방식으로 구현했는데, 이러면 중간에 네트워크 상태가 복원되는 경우 로컬 데이터와 리모트 데이터가 섞임
            // 해결 방법은 좀 더 고민이 필요할 듯
            val data = localDatasource.searchItBook(query, page).getOrNull() ?: emptyList()

            return LoadResult.Page(
                data = data.toEntity(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        }
    }

    private fun List<GetSearchResponseDTO.Book>.toDBEntity() = this.map {
        ItBookEntity(
            it.isbn13, it.title, it.subtitle, it.image, it.url, it.price
        )
    }

    private fun List<ItBookEntity>.toEntity() = this.map {
        GetSearchResponseEntity.Book(
            it.title, it.subTitle, it.isbn13, it.price, it.imageUrl, it.url
        )
    }

}