package data.nodes.properties.root

data class RootComponentDescription(
    val key: String,
    val name: String,
    val description: String,
    val remote: Boolean,
    val documentationLinks: Array<String>
)
