package converter.parser

import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import data.nodes.root.RootDocument
import data.Result

class FigmaGsonParser: Parser {
    override fun parse(input: String): Result<RootDocument> {
        try {
            val rootDocument = GsonBuilder()
                .create()
                .fromJson(input, RootDocument::class.java)

            return Result.success(rootDocument)
        } catch (e: JsonParseException) {
            return Result.failure("Failed to parse input: ${e.message}")
        }
    }
}