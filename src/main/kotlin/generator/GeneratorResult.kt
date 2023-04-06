package generator

import com.squareup.kotlinpoet.CodeBlock
import data.nodes.properties.Rectangle

data class GeneratorResult(
    val statement: CodeBlock? = null,
    // TODO: maybe a hasAbsoluteRenderBound flag? probably absoluteRenderBounds is present when statement is present; check more
    val absoluteRenderBounds: Rectangle? = null
)
