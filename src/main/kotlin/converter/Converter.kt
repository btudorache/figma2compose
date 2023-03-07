package converter

class Converter {
    fun convert(figmaInput: String): ConverterResult {
        println(figmaInput)
        return ConverterResult(CompletionStatus.SUCCESS)
    }

    companion object {
        fun createConverter(): Converter {
            return Converter()
        }
    }
}

data class ConverterResult(val completionStatus: CompletionStatus)

enum class CompletionStatus {
    SUCCESS, FAILED, WARNING
}