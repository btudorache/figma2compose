package analyser

import data.nodes.properties.root.RootComponentDescription

data class AnalyserResult(
    val componentType: ComponentType = ComponentType.UNTAGGED,
    val errorMessages: MutableList<String>? = null,
    val warningsMessages: MutableList<String>? = null,
    val listElementMappings: MutableMap<String, RootComponentDescription>? = null
)
