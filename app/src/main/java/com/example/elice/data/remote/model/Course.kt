package com.example.elice.data.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Keep
data class Course(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("short_description") var shortDescription: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("taglist") var taglist: ArrayList<String> = arrayListOf(),
    @SerializedName("logo_file_url") var logoFileUrl: String? = null,
    @SerializedName("image_file_url") var imageFileUrl: String? = null,
) : Serializable