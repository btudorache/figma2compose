package data.nodes.properties

import data.nodes.enums.BlendMode
import data.nodes.enums.EffectType

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
