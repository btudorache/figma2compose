package data.nodes.properties

import data.nodes.enums.BlendMode
import data.nodes.enums.PaintType

data class Paint(
    val type: PaintType,
    val visible: Boolean,
    val opacity: Int,
    val color: Color,
    val blendMode: BlendMode,
    // TODO: continua parsat paint
)
