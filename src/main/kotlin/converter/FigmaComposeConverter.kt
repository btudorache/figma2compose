package converter

import data.Result
import data.nodes.root.RootDocument
import converter.parser.FigmaGsonParser
import converter.parser.Parser

class FigmaComposeConverter(
    private val parser: Parser = FigmaGsonParser()
): Converter {
    override fun convert(input: String): Result<ConverterResult> {
        val parserResult = parser.parse(input)
        if (parserResult.hasError) {
            return Result.failure("Parser failed: ${parserResult.errorMessage}")
        }

        val componentRoot = parserResult.data as RootDocument
        print(componentRoot.document.pages[0].frames[0])
        return Result.success(ConverterResult(CompletionStatus.SUCCESS))
    }
}
