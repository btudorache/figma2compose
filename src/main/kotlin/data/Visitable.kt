package data

import converter.Visitor

interface Visitable {
    fun <T> accept(visitor: Visitor<T>): T
}