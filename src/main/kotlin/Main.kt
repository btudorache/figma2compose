import converter.Converter
import client.FigmaFileClient
import contracts.Result

fun getUsageText(): String = """
            Invalid arguments. 2 arguments are required.
            Args: [loadFromAPI | loadFromFS] [APIFileId | filePath]          
        """.trimIndent()
fun main(args: Array<String>) {
    if (args.size != 2) {
        println(getUsageText())
        return
    }

    val fileClient = FigmaFileClient()
    val figmaFileResult: Result<String> = when (args[0]) {
        "loadFromAPI" -> fileClient.loadFromApi(args[1])
        "loadFromFS" -> fileClient.loadFromFileStorage(args[1])
        else -> {
            println(getUsageText())
            return
        }
    }

    if (figmaFileResult.hasError) {
        println("File client error: ${figmaFileResult.errorMessage}")
        return
    }

    val converterResult = Converter().convert(figmaFileResult.data as String)
}