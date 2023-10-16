package com.example.elice.api

import com.example.elice.data.remote.model.CourseResponse
import com.example.elice.data.remote.model.CoursesResponse
import com.example.elice.data.remote.model.LectureResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteRepository {
    suspend fun getCourses(
        isRecommended: Boolean?,
        isFree: Boolean?,
        conditions: String?,
        offset: Int,
        count: Int
    ): Flow<Response<CoursesResponse>>

    suspend fun getCourseById(id: String): Flow<Response<CourseResponse>>

    suspend fun getLectureById(id: String): Flow<Response<LectureResponse>>

}