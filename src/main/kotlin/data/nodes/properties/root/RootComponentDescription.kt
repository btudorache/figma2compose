package data.nodes.properties.root

import data.nodes.properties.DocumentationLink

data class RootComponentDescription(
    val key: String,
    val name: String,
    val description: String,
    val remote: Boolean,
    val documentationLinks: Array<DocumentationLink>
)
