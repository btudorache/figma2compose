package data.nodes.root

import com.google.gson.annotations.SerializedName
import data.nodes.Document

data class RootDocument(
    val document: Document,
    @SerializedName("components") val componentDescriptions: Map<String, RootComponentDescription>,
    val componentSets: Map<String, RootComponentSet>,
    val schemaVersion: Int,
    val styles: Map<String, RootStyle>,
    val name: String,
    val lastModified: String,
    val version: String

//    @Transient var additionalField: String
)
