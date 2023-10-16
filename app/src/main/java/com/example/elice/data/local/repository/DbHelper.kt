package com.example.elice.data.local.repository

import com.example.elice.data.local.model.CourseLocal
import kotlinx.coroutines.flow.Flow

interface DbHelper {

    suspend fun isEnrolled(id: Int): Boolean

    suspend fun insert(item: CourseLocal)

    suspend fun delete(id: Int)

    suspend fun getAllEnrolled() : Flow<List<CourseLocal>>

}