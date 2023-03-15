package data.nodes

import com.google.gson.annotations.SerializedName
import data.nodes.enums.BlendMode
import data.nodes.enums.NodeType
import data.nodes.properties.LayoutConstraint
import data.nodes.properties.Paint
import data.nodes.properties.Rectangle

data class Frame(
    val id: String,
    val name: String,
    val type: NodeType,
    val blendMode: BlendMode,
    @SerializedName("children") val components: Array<Component>,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle,
    val constraints: LayoutConstraint,
    val clipsContent: Boolean,
    val fills: Array<Paint>
    // TODO: continua parsat frame
)
