package com.example.elice.data.local.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.elice.data.local.model.CourseLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(course: CourseLocal)

    @Query("SELECT EXISTS(SELECT * FROM courses WHERE courseId = :id)")
    fun isRowIsExist(id: Int): Boolean

    @Query("DELETE FROM courses WHERE courseId = :id")
    fun deleteCourse(id: Int)

    @Query("SELECT * FROM courses ORDER BY id")
    fun getAllEnrolled(): Flow<List<CourseLocal>>

}