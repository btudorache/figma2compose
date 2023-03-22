package data.nodes

import com.google.gson.annotations.SerializedName
import data.AdditionalData
import data.Visitor
import data.Visitable
import data.nodes.properties.Color
import data.nodes.properties.FlowStartingPoint
import data.nodes.properties.PrototypeDevice

data class Page(
    val id: String,
    val name: String,
    // The node type for a page is CANVAS in the figma json
    val type: NodeType,
    @SerializedName("children") val frames: Array<Frame>,
    val backgroundColor: Color,
    val flowStartingPoints: Array<FlowStartingPoint>,
    val prototypeDevice: PrototypeDevice
) : TreeNode() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}
