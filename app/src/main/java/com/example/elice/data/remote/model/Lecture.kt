package com.example.elice.data.remote.model

import com.google.gson.annotations.SerializedName

data class Lecture(
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("order_no") var orderNo: Int? = null
)
