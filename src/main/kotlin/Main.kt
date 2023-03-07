import client.FigmaClient
import converter.Converter

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Token id expected as first parameter")
        return
    }

    val client = FigmaClient()
    val figmaFileResult = client.fetchFigmaFile(args[0])
    if (figmaFileResult.hasError) {
        println("client error: ${figmaFileResult.errorMessage}")
        return
    }

    val converter = Converter.createConverter()
    converter.convert(figmaFileResult.data as String)
}