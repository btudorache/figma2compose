package data.nodes.properties.root

import data.nodes.enums.StyleType

data class RootStyle(
    val key: String,
    val name: String,
    val styleType: StyleType,
    val remote: Boolean,
    val description: String
)
