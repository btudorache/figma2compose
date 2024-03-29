package data.nodes

import data.AdditionalData
import data.Visitor
import data.nodes.enums.*
import data.nodes.properties.*
import data.nodes.properties.Vector

data class Text(
    // shared properties with Vector (except fillOverrideTable)
    val blendMode: BlendMode,
    val preserveRatio: Boolean,
    val layoutAlign: LayoutAlign,
    val layoutGrow: Int,
    val constraints: LayoutConstraint,
    val transitionNodeID: String,
    val transitionDuration: Int,
    val transitionEasing: EasingType,
    val opacity: Int,
    val absoluteBoundingBox: Rectangle,
    val absoluteRenderBounds: Rectangle,
    val effects: Array<Effect>,
    val size: Vector,
    val isMask: Boolean,
    val fills: Array<Paint>,
    val strokes: Array<Paint>,
    val strokeWeight: Int,
    val strokeAlign: StrokeAlign,
    // key is of type StyleType
    val styles: Map<String, String>,

    // specific Text properties
    val characters: String,
    val style: TypeStyle,
    val characterStyleOverrides: Array<Int>,
    val styleOverrideTable: Map<Int, TypeStyle>,
    val lineTypes: Array<LineType>,
    val lineIdentations: Array<Int>
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}
