package data

import com.google.gson.annotations.SerializedName

data class Document(
    val id: String,
    val name: String,
    @SerializedName("children") val pages: Any
)
