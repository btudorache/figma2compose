package data.nodes

import data.AdditionalData
import data.Visitor
import data.nodes.enums.BlendMode
import data.nodes.enums.EasingType
import data.nodes.enums.LayoutAlign
import data.nodes.enums.StyleType
import data.nodes.properties.*
import data.nodes.properties.Vector

data class Vector(
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
    // key is of type StyleType
    val styles: Map<String, String>
) : BaseComponent() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}
