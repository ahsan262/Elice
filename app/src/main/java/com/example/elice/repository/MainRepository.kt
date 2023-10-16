package com.example.elice.repository

import com.example.elice.api.RemoteRepository
import com.example.elice.data.local.model.CourseLocal
import com.example.elice.data.local.repository.LocalRepository
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) {

    suspend fun getCourses(
        isRecommended: Boolean?,
        isFree: Boolean?,
        conditions: String?,
        offset: Int,
        count: Int
    ) = remoteRepository.getCourses(isRecommended, isFree, conditions, offset, count)


    suspend fun getCourseById(id: String) = remoteRepository.getCourseById(id)

    suspend fun getLecturesById(id: String) = remoteRepository.getLectureById(id)

    suspend fun insertCourse(item: CourseLocal) {
        localRepository.insert(item)
    }

    suspend fun isEnrolled(id: Int): Boolean {
        return localRepository.isEnrolled(id)
    }

    suspend fun delete(id: Int) {
        localRepository.delete(id)
    }

    suspend fun getAllEnrolled() = localRepository.getAllEnrolled()

}