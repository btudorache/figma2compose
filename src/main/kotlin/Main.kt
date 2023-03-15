import converter.Converter
import client.FigmaFileClient
import converter.FigmaComposeConverter
import data.Result

fun getUsageText(): String = """
            Invalid command line arguments.
            Usage:
                Load from api: loadFromApi [apiFileId]
                Load from file system: loadFromFs [filePath]          
        """.trimIndent()

fun main(args: Array<String>) {
    if (args.size != 2) {
        println(getUsageText())
        return
    }

    val fileClient = FigmaFileClient()
    val figmaFileResult: Result<String> = when (args[0]) {
        "loadFromApi" -> fileClient.loadFromApi(args[1])
        "loadFromFs" -> fileClient.loadFromFileStorage(args[1])
        else -> {
            println(getUsageText())
            return
        }
    }

    if (figmaFileResult.hasError) {
        println("File client error: ${figmaFileResult.errorMessage}")
        return
    }

    val converter = FigmaComposeConverter()
    val converterResult = converter.convert(figmaFileResult.data as String)
    if (converterResult.hasError) {
        println("Converter error: ${converterResult.errorMessage}")
    }
}