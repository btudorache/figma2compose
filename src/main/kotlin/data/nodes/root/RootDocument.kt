package data.nodes.root

import com.google.gson.annotations.SerializedName
import data.Visitor
import data.nodes.Document
import data.Visitable

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
) : Visitable {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
