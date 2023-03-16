package data

interface Visitable {
    fun <T> accept(visitor: Visitor<T>): T
}