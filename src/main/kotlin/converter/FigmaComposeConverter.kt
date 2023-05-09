package converter

import analyser.AnalyserResult
import analyser.AnalyserVisitor
import analyser.PrintVisitor
import data.Result
import data.Visitor
import data.nodes.RootDocument
import generator.ComposeGeneratorVisitor
import generator.GeneratorResult
import parser.FigmaGsonParser
import parser.Parser

class FigmaComposeConverter(
    private val parser: Parser = FigmaGsonParser(),
    private val analyser: Visitor<AnalyserResult> = AnalyserVisitor(),
    private val generator: Visitor<GeneratorResult> = ComposeGeneratorVisitor()
): Converter {
    override fun convert(input: String): Result<ConverterResult> {
        val parserResult = parser.parse(input)
        if (parserResult.hasError) {
            return Result.failure("Parser failed: ${parserResult.errorMessage}")
        }

        val componentRoot = parserResult.data as RootDocument

        val analyserResult = componentRoot.accept(analyser)
        // TODO do various checks on the analyserResult
        if (!analyserResult.errorMessages.isNullOrEmpty()) {
            return Result.failure("Failed to analyse input: ${analyserResult.errorMessages}")
        }

        if (analyserResult.listElementMappings != null) {
            (generator as ComposeGeneratorVisitor).setListElementMappings(analyserResult.listElementMappings)
        }

        // used for debugging
        componentRoot.accept(PrintVisitor())

        val generatorResult = componentRoot.accept(generator)
        // TODO do various checks on the generatorResult

        return Result.success(ConverterResult(CompletionStatus.SUCCESS))
    }
}
