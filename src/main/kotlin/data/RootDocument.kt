package data

data class RootDocument(
    val document: Document,
    val components: Any,
    val componentSets: Any,
    val schemaVersion: String,
    val styles: Any,
    val name: String,
    val version: String
//    @Transient var additionalField: String
)
