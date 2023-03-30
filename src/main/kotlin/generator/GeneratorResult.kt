package generator

import com.squareup.kotlinpoet.CodeBlock
import data.nodes.properties.Rectangle

data class GeneratorResult(
    val statement: CodeBlock? = null,
    // TODO: maybe a hasAbsoluteRenderBound flag?
    val absoluteRenderBounds: Rectangle? = null
)
