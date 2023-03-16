import client.FigmaFileClient
import converter.FigmaComposeConverter
import data.Result

enum class Command(val commandName: String) {
    LOAD_FROM_API("loadFromApi"),
    LOAD_FROM_FS("loadFromFs")
}

fun getUsageText(args: Array<String>): String = """
            Invalid command line arguments: ${args.joinToString(separator = " ")}
            Usage:
                Load from api: ${Command.LOAD_FROM_API.commandName} [apiFileId]
                Load from file system: ${Command.LOAD_FROM_FS.commandName} [filePath]. Works with both relative and absolute paths       
        """.trimIndent()

fun main(args: Array<String>) {
    if (args.size != 2) {
        println(getUsageText(args))
        return
    }

    val fileClient = FigmaFileClient()
    val figmaFileResult: Result<String> = when (args[0]) {
        Command.LOAD_FROM_API.commandName -> fileClient.loadFromApi(args[1])
        Command.LOAD_FROM_FS.commandName -> fileClient.loadFromFileStorage(args[1])
        else -> {
            println(getUsageText(args))
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