package data

interface Visitable {
    fun <T> accept(visitor: Visitor<T>, additionalData: Any? = null): T
}