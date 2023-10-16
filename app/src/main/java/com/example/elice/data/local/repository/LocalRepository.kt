package com.example.elice.data.local.repository

import com.example.elice.data.local.model.CourseLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepository @Inject constructor(private val dao: CourseDao) : DbHelper {
    override suspend fun isEnrolled(id: Int): Boolean {
        return dao.isRowIsExist(id)
    }

    override suspend fun insert(item: CourseLocal) {
        dao.insert(item)
    }

    override suspend fun delete(id: Int) {
        dao.deleteCourse(id)
    }

    override suspend fun getAllEnrolled(): Flow<List<CourseLocal>> {
        return dao.getAllEnrolled()
    }

}