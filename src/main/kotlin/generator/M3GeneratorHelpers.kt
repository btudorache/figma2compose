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
        private const val MATERIAL3_ELEVATED_BUTTON_IMPORT = "androidx.compose.material3.ElevatedButton"
        private const val MATERIAL3_OUTLINED_BUTTON_IMPORT = "androidx.compose.material3.OutlinedButton"
        private const val MATERIAL3_TEXT_BUTTON_IMPORT = "androidx.compose.material3.TextButton"
        private const val MATERIAL3_RADIO_BUTTON_IMPORT = "androidx.compose.material3.RadioButton"
        private const val MATERIAL3_FILLED_TONAL_BUTTON_IMPORT = "androidx.compose.material3.FilledTonalButton"
        private const val MATERIAL3_BUTTON_IMPORT = "androidx.compose.material3.Button"

        private const val MATERIAL3_TEXT_IMPORT = "androidx.compose.material3.Text"
        private const val MATERIAL3_TEXTFIELD_IMPORT = "androidx.compose.material3.TextField"
        private const val MATERIAL3_OUTLINED_TEXTFIELD_IMPORT = "androidx.compose.material3.OutlinedTextField"

        private const val MATERIAL3_ICON_IMPORT = "androidx.compose.material3.Icon"
        private const val MATERIAL3_SWITCH_IMPORT = "androidx.compose.material3.Switch"
        private const val MATERIAL3_CHECKBOX_IMPORT = "androidx.compose.material3.Checkbox"

        private const val MATERIAL3_LIST_ITEM_IMPORT = "androidx.compose.material3.ListItem"

        const val m3ListItemMultiplier = 3
        private fun generateM3Button(instance: Instance, currentImports: MutableSet<String>, componentDescription: RootComponentDescription?): GeneratorResult {
            var buttonType = "Button"
            if (componentDescription != null) {
                val buttonConfiguration = componentDescription.name.split(", ")[0].split("=")[1]
                buttonType = when (buttonConfiguration) {
                    ButtonType.ELEVATED.type -> {
                        currentImports.add(MATERIAL3_ELEVATED_BUTTON_IMPORT)
                        "ElevatedButton"
                    }
                    ButtonType.OUTLINED.type -> {
                        currentImports.add(MATERIAL3_OUTLINED_BUTTON_IMPORT)
                        "OutlinedButton"
                    }
                    ButtonType.TEXT.type -> {
                        currentImports.add(MATERIAL3_TEXT_BUTTON_IMPORT)
                        "TextButton"
                    }
                    ButtonType.FILLED.type,
                    ButtonType.TONAL.type -> {
                        currentImports.add(MATERIAL3_FILLED_TONAL_BUTTON_IMPORT)
                        "FilledTonalButton"
                    }
                    else -> {
                        currentImports.add(MATERIAL3_BUTTON_IMPORT)
                        "Button"
                    }
                }
            } else {
                currentImports.add(MATERIAL3_BUTTON_IMPORT)
            }
            currentImports.add(MATERIAL3_TEXT_IMPORT)

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
                        currentImports.add(MATERIAL3_TEXTFIELD_IMPORT)
                        "TextField"
                    }
                    TextFieldType.OUTLINED.type -> {
                        currentImports.add(MATERIAL3_OUTLINED_TEXTFIELD_IMPORT)
                        "OutlinedTextField"
                    }
                    else -> {
                        currentImports.add(MATERIAL3_TEXTFIELD_IMPORT)
                        "TextField"
                    }
                }
            } else {
                currentImports.add(MATERIAL3_BUTTON_IMPORT)
            }
            currentImports.add(MATERIAL3_TEXT_IMPORT)

            val codeBlockBuilder = CodeBlock.builder().addStatement("${textFieldType}(value = \"\", onValueChange = {}, label = { Text(\"Label\") }, supportingText = { Text(\"Supporting text\") }, ${GeneratorHelpers.generateModifier(instance)})")
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        }

        private fun generateM3ListItem(instance: Instance,
                                       currentImports: MutableSet<String>,
                                       componentDescription: RootComponentDescription?,
                                      ): GeneratorResult {
            val listElemDescriptionMap = componentDescription!!.name
                .split(", ")
                .associate {
                    val (left, right) = it.split("=")
                    left to right
                }
            val listElemProperties = mutableListOf("headlineContent = { Text(\"headline text\") }")

            when (listElemDescriptionMap["Leading"]!!) {
                "Icon" -> {
                    listElemProperties.add("leadingContent = { Icon(Icons.Default.Person, contentDescription = \"Localized description\") }")
                    currentImports.add(MATERIAL3_ICON_IMPORT)
                    currentImports.add("androidx.compose.material.icons.Icons")
                }
                "Check box" -> {
                    listElemProperties.add("leadingContent = { Checkbox(checked=true,onCheckedChange={}) }")
                    currentImports.add(MATERIAL3_CHECKBOX_IMPORT)
                }
                "Radio Button" -> {
                    listElemProperties.add("leadingContent = { RadioButton(selected=true,onClick={}) }")
                    currentImports.add(MATERIAL3_RADIO_BUTTON_IMPORT)
                }
                "Switch" -> {
                    listElemProperties.add("leadingContent = { Switch(checked=true,onCheckedChange={}) }")
                    currentImports.add(MATERIAL3_SWITCH_IMPORT)
                }
            }

            when (listElemDescriptionMap["Trailing"]!!) {
                "Icon" -> {
                    listElemProperties.add("trailingContent = { Icon(Icons.Default.Person, contentDescription = \"Localized description\") }")
                    currentImports.add(MATERIAL3_ICON_IMPORT)
                    currentImports.add("androidx.compose.material.icons.Icons")
                }
                "Check box" -> {
                    listElemProperties.add("trailingContent = { Checkbox(checked=true,onCheckedChange={}) }")
                    currentImports.add(MATERIAL3_CHECKBOX_IMPORT)
                }
                "Radio Button" -> {
                    listElemProperties.add("trailingContent = { RadioButton(selected=true,onClick={}) }")
                    currentImports.add(MATERIAL3_RADIO_BUTTON_IMPORT)
                }
                "Switch" -> {
                    listElemProperties.add("trailingContent = { Switch(checked=true,onCheckedChange={}) }")
                    currentImports.add(MATERIAL3_SWITCH_IMPORT)
                }
            }

            if (listElemDescriptionMap["Show overline"]!!.toBoolean()) {
                listElemProperties.add("overlineContent = { Text(\"Overline text\") }")
            }

            if (listElemDescriptionMap["Show supporting text"]!!.toBoolean()) {
                listElemProperties.add("supportingContent = { Text(\"Supporting text\") }")
            }

            currentImports.add(MATERIAL3_LIST_ITEM_IMPORT)
            val codeBlockBuilder = CodeBlock.builder().addStatement("ListItem(${listElemProperties.joinToString(separator = ",")})")
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        }

        fun generateM3Component(instance: Instance,
                                componentDescriptions: Map<String, RootComponentDescription>,
                                currentImports: MutableSet<String>
                               ): GeneratorResult {
            val componentDescription = componentDescriptions.getValue(instance.componentId)
            return when (instance.componentType) {
                ComponentType.M3_BUTTON -> {
                    generateM3Button(instance, currentImports, componentDescription)
                }
                ComponentType.M3_TEXT_FIELD -> {
                    generateM3TextField(instance, currentImports, componentDescription)
                }
                ComponentType.M3_CHECKBOX -> {
                    generateM3CheckBox(instance, currentImports, componentDescription)
                }
                ComponentType.M3_LIST_ITEM -> {
                    generateM3ListItem(instance, currentImports, componentDescription)
                }
                ComponentType.M3_SWITCH -> {
                    generateM3Switch(instance, currentImports, componentDescription)
                }
                else -> {
                    GeneratorResult()
                }
            }
        }

        private fun generateM3Switch(
            instance: Instance,
            currentImports: MutableSet<String>,
            componentDescription: RootComponentDescription
        ): GeneratorResult {
            val switchState = componentDescription.name.split(", ")[0].split("=")[1]
            currentImports.add(MATERIAL3_CHECKBOX_IMPORT)

            val codeBlockBuilder = CodeBlock.builder().addStatement("Switch(checked=${switchState},onCheckedChange={})")
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        }
        private fun generateM3CheckBox(
            instance: Instance,
            currentImports: MutableSet<String>,
            componentDescription: RootComponentDescription
        ): GeneratorResult {
            val checkboxStateText = componentDescription.name.split(", ")[0].split("=")[1]
            val checkboxState = if (checkboxStateText == "Selected") "true" else "false"
            currentImports.add(MATERIAL3_SWITCH_IMPORT)

            val codeBlockBuilder = CodeBlock.builder().addStatement("Checkbox(checked=${checkboxState},onCheckedChange={})")
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        }
    }
}