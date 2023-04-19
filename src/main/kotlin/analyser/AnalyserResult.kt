package analyser

data class AnalyserResult(
    val componentType: ComponentType = ComponentType.UNTAGGED,
    val errorMessages: MutableList<String>? = null,
    val warningsMessages: MutableList<String>? = null,
)
