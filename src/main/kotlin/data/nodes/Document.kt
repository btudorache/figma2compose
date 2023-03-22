package data.nodes

import com.google.gson.annotations.SerializedName
import data.AdditionalData
import data.Visitor
import data.Visitable

data class Document(
    val id: String,
    val name: String,
    val type: NodeType,
    @SerializedName("children") val pages: Array<Page>
) : TreeNode() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}
