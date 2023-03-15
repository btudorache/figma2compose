package converter

data class ConverterResult(val completionStatus: CompletionStatus)

enum class CompletionStatus {
    SUCCESS, FAILED, WARNING
}
