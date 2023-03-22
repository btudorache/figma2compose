package data.nodes

import analyser.ComponentType
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import data.AdditionalData
import data.Visitor
import data.nodes.enums.*
import data.nodes.properties.Effect
import data.nodes.properties.LayoutConstraint
import data.nodes.properties.Paint
import data.nodes.properties.Rectangle

data class Frame(
    val blendMode: BlendMode,
    @SerializedName("children") val components: Array<BaseComponent>,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle,
    val constraints: LayoutConstraint,
    val clipsContent: Boolean,
    val fills: Array<Paint>,
    val strokes: Array<Paint>,
    val cornerRadius: Int,
    val strokeWeight: Int,
    val strokeAlign: StrokeAlign,
    val styles: Map<String, String>,
    val layoutMode: LayoutMode,
    val itemSpacing: Int,
    val counterAxisAlignItems: AxisSizingMode,
    val primaryAxisAlignItems: AxisSizingMode,
    val paddingLeft: Int,
    val paddingRight: Int,
    val paddingTop: Int,
    val paddingBottom: Int,
    val effects: Array<Effect>,

    @Expose(serialize = false, deserialize = false)
    var componentType: ComponentType
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}
