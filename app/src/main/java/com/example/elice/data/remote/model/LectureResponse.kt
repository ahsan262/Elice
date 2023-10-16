package com.example.elice.data.remote.model

import com.google.gson.annotations.SerializedName

data class LectureResponse(
    @SerializedName("_result") var Result: Result? = Result(),
    @SerializedName("lectures") var lectures: ArrayList<Lecture> = arrayListOf(),
)
