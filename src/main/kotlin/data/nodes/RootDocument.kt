package data.nodes

import com.google.gson.annotations.SerializedName
import data.AdditionalData
import data.Visitor
import data.nodes.properties.root.RootComponentDescription
import data.nodes.properties.root.RootComponentSet
import data.nodes.properties.root.RootStyle

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
) : TreeNode() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}
