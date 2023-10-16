package com.example.elice.api

import com.example.elice.data.remote.model.CourseResponse
import com.example.elice.data.remote.model.CoursesResponse
import com.example.elice.data.remote.model.LectureResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("course/list/")
    suspend fun getCourses(
        @Query("filter_is_recommended") isRecommended: Boolean?,
        @Query("filter_is_free") isFree: Boolean?,
        @Query("filter_conditions") conditions: String?,
        @Query("offset") offset: Int,
        @Query("count") count: Int
    ): Response<CoursesResponse>

    @GET("course/get/")
    suspend fun getCourseById(
        @Query("course_id") id: String
    ): Response<CourseResponse>

    @GET("lecture/list/")
    suspend fun getLectureById(
        @Query("course_id") id: String,
        @Query("offset") offset: Int = 0,
        @Query("count") count: Int = 40

    ): Response<LectureResponse>

}