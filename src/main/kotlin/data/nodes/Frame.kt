package data.nodes

import com.google.gson.annotations.SerializedName
import data.Visitor
import data.nodes.enums.BlendMode
import data.nodes.enums.StrokeAlign
import data.nodes.properties.Effect
import data.nodes.properties.LayoutConstraint
import data.nodes.properties.Paint
import data.nodes.properties.Rectangle

data class Frame(
    val blendMode: BlendMode,
    @SerializedName("children") val components: Array<Component>,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle,
    val constraints: LayoutConstraint,
    val clipsContent: Boolean,
    val fills: Array<Paint>,
    val strokes: Array<Paint>,
    val strokeWeight: Int,
    val strokeAlign: StrokeAlign,
    val effects: Array<Effect>
) : Component() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
