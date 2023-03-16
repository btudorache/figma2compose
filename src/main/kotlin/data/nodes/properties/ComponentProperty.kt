package data.nodes.properties

import data.nodes.properties.enums.ComponentPropertyType

data class ComponentProperty(
    val type: ComponentPropertyType,
    val value: String // or Boolean as per the docs
)
