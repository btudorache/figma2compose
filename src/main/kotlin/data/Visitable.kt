package data

// TODO: asses generic structure for additional data
interface Visitable {
    fun <T> accept(visitor: Visitor<T>, additionalData: AdditionalData? = null): T
}