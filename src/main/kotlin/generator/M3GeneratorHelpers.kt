package generator

import analyser.ComponentType
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock
import data.nodes.Frame
import data.nodes.Instance
import data.nodes.Text
import data.nodes.properties.root.RootComponentDescription
import generator.M3Types.ButtonType
import generator.M3Types.TextFieldType

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
            currentImports.add("androidx.compose.material3.Text")

            val codeBlockBuilder = CodeBlock.builder()
            codeBlockBuilder.beginControlFlow("${buttonType}(onClick = {}, ${GeneratorHelpers.generateButtonModifier(instance.absoluteRenderBounds, instance.fills, true)})")
            // you can be sure that the following casts are correct; they would have failed in the analysis layer otherwise
            val stateLayerFrame = instance.components[0] as Frame
            val textNode = stateLayerFrame.components.last() as Text
            codeBlockBuilder.add(buildCodeBlock { addStatement("Text(${GeneratorHelpers.generateModifier(textNode)})") })
            codeBlockBuilder.endControlFlow()
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        }

        private fun generateM3TextField(instance: Instance, currentImports: MutableSet<String>, componentDescription: RootComponentDescription?): GeneratorResult {
            var textFieldType = "TextField"
            if (componentDescription != null) {
                val textFieldConfiguration = componentDescription.name.split(", ")[0].split("=")[1]
                textFieldType = when (textFieldConfiguration) {
                    TextFieldType.FILLED.type -> {
                        currentImports.add("androidx.compose.material3.TextField")
                        "TextField"
                    }
                    TextFieldType.OUTLINED.type -> {
                        currentImports.add("androidx.compose.material3.OutlinedTextField")
                        "OutlinedTextField"
                    }
                    else -> {
                        currentImports.add("androidx.compose.material3.Button")
                        "TextField"
                    }
                }
            } else {
                currentImports.add("androidx.compose.material3.Button")
            }
            currentImports.add("androidx.compose.material3.Text")

            val codeBlockBuilder = CodeBlock.builder().addStatement("${textFieldType}(value = \"\", onValueChange = {}, label = { Text(\"Label\") }, ${GeneratorHelpers.generateModifier(instance)})")
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        }

        private fun generateM3List(instance: Instance,
                                   currentImports: MutableSet<String>,
                                   componentDescription: RootComponentDescription?,
                                   listElementMappings: MutableMap<String, RootComponentDescription>
                                ): GeneratorResult {
            // TODO: generate m3 list
            return GeneratorResult()
        }

        fun generateM3Component(instance: Instance,
                                componentDescriptions: Map<String, RootComponentDescription>,
                                currentImports: MutableSet<String>,
                                listElementMappings: MutableMap<String, RootComponentDescription>
                            ): GeneratorResult {
            val componentDescription = componentDescriptions.getValue(instance.componentId)
            return when (instance.componentType) {
                ComponentType.M3_BUTTON -> {
                    generateM3Button(instance, currentImports, componentDescription)
                }
                ComponentType.M3_TEXT_FIELD -> {
                    generateM3TextField(instance, currentImports, componentDescription)
                }
                ComponentType.M3_LIST -> {
                    generateM3List(instance, currentImports, componentDescription, listElementMappings)
                }
                ComponentType.M3_LIST_ITEM -> {
                    GeneratorResult()
                }
                else -> {
                    GeneratorResult()
                }
            }
        }
    }
}