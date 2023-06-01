package data.nodes

import com.google.gson.annotations.SerializedName
import data.AdditionalData
import data.Visitor
import data.nodes.enums.BlendMode
import data.nodes.enums.BooleanOperationType
import data.nodes.enums.EasingType
import data.nodes.enums.LayoutAlign
import data.nodes.properties.*
import data.nodes.properties.Vector

class BooleanOperation(
    // these are shared with Vector
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
    val styles: Map<String, String>,

    // specific properties
    @SerializedName("children") val components: Array<BaseComponent>,
    val booleanOperation: BooleanOperationType
) : BaseComponent() {

    override fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData?): T {
        return visitor.visit(this, additionalData)
    }
}