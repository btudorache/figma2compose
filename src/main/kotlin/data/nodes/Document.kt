package data.nodes

import com.google.gson.annotations.SerializedName
import data.nodes.enums.NodeType

data class Document(
    val id: String,
    val name: String,
    val type: NodeType,
    @SerializedName("children") val pages: Array<Page>
)
