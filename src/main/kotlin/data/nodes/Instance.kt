package data.nodes

import com.google.gson.annotations.SerializedName
import data.AdditionalData
import data.Visitor
import data.nodes.enums.*
import data.nodes.properties.*

// TODO: Class shares part of properties with Frame, maybe find a decent way to refactor
// TODO: Find a way to have as little duplication as possible between components in data.nodes
data class Instance(
    // Shared properties (with Frame)
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
    // key is of type StyleType
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

    // Specific Instance properties
    val componentId: String,
    val isExposedInstance: Boolean,
    val exposedInstances: Array<String>,
    val componentProperties: Map<String, ComponentProperty>,
    val overrides: Array<Overrides>
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}
