package data.nodes.properties

import data.nodes.properties.enums.BlendMode
import data.nodes.properties.enums.EffectType

data class Effect(
    val type: EffectType,
    val visible: Boolean,
    val radius: Int,
    val color: Color,
    val blendMode: BlendMode,
    val offset: Vector,
    val spread: Int,
    val showShadowBehindNode: Boolean
)
