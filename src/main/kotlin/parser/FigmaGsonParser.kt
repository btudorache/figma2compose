package parser

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonParseException
import data.Result
import data.nodes.*
import data.nodes.RootDocument

class FigmaGsonParser: Parser {
    override fun parse(input: String): Result<RootDocument> {
        try {
            val baseComponentAdapter = JsonDeserializer<BaseComponent> { json, typeOfT, context ->
                val nodeObject = json.asJsonObject
                val nodeTypeElement = nodeObject.get(BaseComponent::type.name)
                return@JsonDeserializer when (nodeTypeElement.asString) {
                    NodeType.TEXT.name -> context.deserialize(json, Text::class.java)
                    NodeType.INSTANCE.name -> context.deserialize(json, Instance::class.java)
                    NodeType.FRAME.name -> context.deserialize(json, Frame::class.java)
                    NodeType.COMPONENT.name -> context.deserialize(json, Component::class.java)
                    NodeType.VECTOR.name -> context.deserialize(json, Vector::class.java)
                    NodeType.LINE.name -> context.deserialize(json, Line::class.java)
                    NodeType.RECTANGLE.name -> context.deserialize(json, RectangleNode::class.java)
                    else -> context.deserialize(json, BaseComponent::class.java)
                }
            }

            val rootDocument = GsonBuilder()
                .registerTypeAdapter(BaseComponent::class.java, baseComponentAdapter)
                .create()
                .fromJson(input, RootDocument::class.java)

            return Result.success(rootDocument)
        } catch (e: JsonParseException) {
            return Result.failure("Failed to parse input: ${e.message} ${e.localizedMessage} ${e.stackTrace}")
        }
    }
}