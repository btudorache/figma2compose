package converter

import analyser.AnalyserResult
import analyser.DummyVisitor
import data.Result
import data.Visitor
import data.nodes.root.RootDocument
import generator.ComposeGeneratorVisitor
import generator.GeneratorResult
import parser.FigmaGsonParser
import parser.Parser

class FigmaComposeConverter(
    private val parser: Parser = FigmaGsonParser(),
    private val analyser: Visitor<AnalyserResult> = DummyVisitor(),
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

        val generatorResult = componentRoot.accept(generator)
        // TODO do various checks on the generatorResult

        return Result.success(ConverterResult(CompletionStatus.SUCCESS))
    }
}
