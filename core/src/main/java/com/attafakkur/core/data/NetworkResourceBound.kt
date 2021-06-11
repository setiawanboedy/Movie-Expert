package com.attafakkur.core.data

import com.attafakkur.core.data.source.network.api.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkResourceBound<ResultType, RequestType> {
    private var result: Flow<State<out ResultType?>> = flow {
        emit(State.Loading())
        val dbSource = loadFromDB()?.first()
        if (shouldFetch(dbSource)) {
            emit(State.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    loadFromDB()?.let { data -> emitAll(data.map { State.Success(it) }) }
                }
                is ApiResponse.Empty -> {
                    loadFromDB()?.let { data -> emitAll(data.map { State.Success(it) }) }
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(State.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            loadFromDB()?.let { data -> emitAll(data.map { State.Success(it) }) }
        }
    }

    protected open fun onFetchFailed() {}
    protected abstract fun loadFromDB(): Flow<ResultType?>?
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<State<out ResultType?>> = result
}