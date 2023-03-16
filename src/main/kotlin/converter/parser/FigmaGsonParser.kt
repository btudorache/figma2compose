package converter.parser

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonParseException
import data.Result
import data.nodes.*
import data.nodes.root.RootDocument

class FigmaGsonParser: Parser {
    override fun parse(input: String): Result<RootDocument> {
        try {
            val componentAdapter = JsonDeserializer<Component> { json, typeOfT, context ->
                val nodeObject = json.asJsonObject
                val nodeTypeElement = nodeObject.get(Component::type.name)
                return@JsonDeserializer when (nodeTypeElement.asString) {
                    NodeType.TEXT.name -> context.deserialize(json, Text::class.java)
                    NodeType.INSTANCE.name -> context.deserialize(json, Instance::class.java)
                    NodeType.FRAME.name -> context.deserialize(json, Frame::class.java)
                    else -> context.deserialize(json, Component::class.java)
                }
            }

            val rootDocument = GsonBuilder()
                .registerTypeAdapter(Component::class.java, componentAdapter)
                .create()
                .fromJson(input, RootDocument::class.java)

            return Result.success(rootDocument)
        } catch (e: JsonParseException) {
            return Result.failure("Failed to parse input: ${e.message}")
        }
    }
}