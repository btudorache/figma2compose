package generator

import com.squareup.kotlinpoet.CodeBlock

data class GeneratorResult(
    val statement: CodeBlock? = null,
    val data: Any? = null
)
