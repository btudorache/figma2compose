package data.nodes.properties

import data.nodes.enums.ComponentPropertyType

data class ComponentPropertyDefinition(
    val type: ComponentPropertyType,
    val defaultValue: String, // or boolean as per the figma docs
    val variantOptions: Array<String>
)
