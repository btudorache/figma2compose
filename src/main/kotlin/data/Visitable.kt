package data

interface Visitable {
    fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData? = null): T
}