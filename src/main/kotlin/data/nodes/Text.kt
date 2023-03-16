package data.nodes

import converter.Visitor

data class Text(
    val characters: String,
) : Component() {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
