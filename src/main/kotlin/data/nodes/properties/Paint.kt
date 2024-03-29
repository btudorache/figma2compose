package data.nodes.properties

import data.nodes.enums.BlendMode
import data.nodes.enums.PaintType
import data.nodes.enums.ScaleMode

data class Paint(
    val type: PaintType,
    val visible: Boolean,
    val opacity: Int,
    val color: Color,
    val blendMode: BlendMode,
    val gradientHandlePositions: Array<Vector>,
    val gradientStops: Array<ColorStop>,
    val scaleMode: ScaleMode,
    val rotation: Number,
    val imageRef: String,
)
