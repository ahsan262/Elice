package com.example.elice.data.local.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.elice.data.local.model.CourseLocal

@Database(entities = [CourseLocal::class], version = 1, exportSchema = false)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}