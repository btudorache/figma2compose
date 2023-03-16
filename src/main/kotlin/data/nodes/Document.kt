package data.nodes

import com.google.gson.annotations.SerializedName
import data.Visitor
import data.Visitable

data class Document(
    val id: String,
    val name: String,
    val type: NodeType,
    @SerializedName("children") val pages: Array<Page>
) : Visitable {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
