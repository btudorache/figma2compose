package generator

import analyser.ComponentType
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import data.nodes.Frame
import data.nodes.Instance
import data.nodes.Text
import data.nodes.properties.root.RootComponentDescription

class M3GeneratorHelpers {
    companion object {
        private fun generateM3Button(instance: Instance, currentImports: MutableSet<String>, componentDescription: RootComponentDescription?): GeneratorResult {
            var buttonType = "Button"
            if (componentDescription != null) {
                val buttonConfiguration = componentDescription.name.split(", ")[0].split("=")[1]
                buttonType = when (buttonConfiguration) {
                    ButtonType.ELEVATED.type -> {
                        currentImports.add("androidx.compose.material3.ElevatedButton")
                        "ElevatedButton"
                    }
                    ButtonType.OUTLINED.type -> {
                        currentImports.add("androidx.compose.material3.OutlinedButton")
                        "OutlinedButton"
                    }
                    ButtonType.TEXT.type -> {
                        currentImports.add("androidx.compose.material3.TextButton")
                        "TextButton"
                    }
                    ButtonType.FILLED.type,
                    ButtonType.TONAL.type -> {
                        currentImports.add("androidx.compose.material3.FilledTonalButton")
                        "FilledTonalButton"
                    }
                    else -> {
                        currentImports.add("androidx.compose.material3.Button")
                        "Button"
                    }
                }
            } else {
                currentImports.add("androidx.compose.material3.Button")
            }

            val codeBlockBuilder = CodeBlock.builder()
            codeBlockBuilder.beginControlFlow("${buttonType}(onClick = {}, ${GeneratorHelpers.generateButtonModifier(instance.absoluteRenderBounds, instance.fills)})")
            // you can be sure that the following casts are correct; they would have failed in the analysis layer otherwise
            val stateLayerFrame = instance.components[0] as Frame
            val textNode = stateLayerFrame.components.last() as Text
            codeBlockBuilder.add(buildCodeBlock { addStatement("Text(${GeneratorHelpers.generateModifier(textNode)})") })
            codeBlockBuilder.endControlFlow()
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        }

        fun generateM3Component(instance: Instance, componentDescriptions: Map<String, RootComponentDescription>, currentImports: MutableSet<String>): GeneratorResult {
            val componentDescription = componentDescriptions.getValue(instance.componentId)
            return when (instance.componentType) {
                ComponentType.M3_BUTTON -> {
                    generateM3Button(instance, currentImports, componentDescription)
                }
                else -> {
                    GeneratorResult()
                }
            }
        }
    }
}