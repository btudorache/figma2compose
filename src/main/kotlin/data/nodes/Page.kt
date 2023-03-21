package data.nodes

import com.google.gson.annotations.SerializedName
import data.Visitor
import data.Visitable
import data.nodes.properties.Color
import data.nodes.properties.FlowStartingPoint
import data.nodes.properties.PrototypeDevice

data class Page(
    val id: String,
    val name: String,
    val type: NodeType,
    @SerializedName("children") val frames: Array<Frame>,
    val backgroundColor: Color,
    val flowStartingPoints: Array<FlowStartingPoint>,
    val prototypeDevice: PrototypeDevice
) : Visitable {
    override fun <T> accept(visitor: Visitor<T>, additionalData: Any?): T {
        return visitor.visit(this)
    }
}
