package com.example.elice.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "courses")
data class CourseLocal(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("course_id") var courseId: Int? = null,
)
