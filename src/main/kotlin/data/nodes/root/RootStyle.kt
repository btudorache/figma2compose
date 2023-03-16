package data.nodes.root

import data.nodes.properties.enums.StyleType

data class RootStyle(
    val key: String,
    val name: String,
    val styleType: StyleType,
    val remote: Boolean,
    val description: String
)
