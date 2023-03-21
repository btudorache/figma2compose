package data.nodes

import data.Visitor

data class Text(
    val characters: String,
) : Component() {
    override fun <T> accept(visitor: Visitor<T>, additionalData: Any?): T {
        return visitor.visit(this, additionalData)
    }
}
