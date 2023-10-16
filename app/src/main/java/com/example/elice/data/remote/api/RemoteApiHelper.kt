package com.example.elice.api

import com.example.elice.data.remote.model.CourseResponse
import com.example.elice.data.remote.model.LectureResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteRepository {

    override suspend fun getCourses(
        isRecommended: Boolean?,
        isFree: Boolean?,
        conditions: String?,
        offset: Int,
        count: Int
    ) = flow {
        emit(apiService.getCourses(isRecommended, isFree, conditions, offset, count))
    }

    override suspend fun getCourseById(id: String): Flow<Response<CourseResponse>> = flow {
        emit(apiService.getCourseById(id))
    }

    override suspend fun getLectureById(id: String): Flow<Response<LectureResponse>> = flow {
        emit(apiService.getLectureById(id))
    }


}