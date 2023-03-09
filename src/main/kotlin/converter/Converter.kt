package converter

import contracts.Result
import parser.FigmaGsonParser
import parser.Parser

class Converter(
    private val parser: Parser = FigmaGsonParser()
) {
    fun convert(figmaInput: String): Result<ConverterResult> {
        val parserResult = parser.parse(figmaInput)
        if (parserResult.hasError) {
            return Result.failure("Parser failed: ${parserResult.errorMessage}")
        }

        println(parserResult.data)
        return Result.success(ConverterResult(CompletionStatus.SUCCESS))
    }
}

data class ConverterResult(val completionStatus: CompletionStatus)

enum class CompletionStatus {
    SUCCESS, FAILED, WARNING
}