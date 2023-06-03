package generator

import analyser.ComponentType
import com.squareup.kotlinpoet.*
import data.AdditionalData
import data.Visitor
import data.nodes.*
import data.nodes.RootDocument
import data.nodes.properties.root.RootComponentDescription
import generator.GeneratorHelpers.Companion.generateButtonModifier
import generator.GeneratorHelpers.Companion.generateModifier
import generator.GeneratorHelpers.Companion.getColorModifier
import generator.GeneratorHelpers.Companion.getModifierType
import java.io.File

class ComposeGeneratorVisitor : Visitor<GeneratorResult> {
    private val OUT_DIRECTORY_PATH = "./compose_out/"
    private lateinit var componentDescriptions: Map<String, RootComponentDescription>
    private lateinit var currentImports: MutableSet<String>
    private lateinit var componentMappings: MutableMap<String, String>

    private lateinit var mainFileBuilder: FileSpec.Builder

    override fun visit(rootDocument: RootDocument, additionalData: AdditionalData?): GeneratorResult {
        this.componentDescriptions = rootDocument.componentDescriptions
        rootDocument.document.accept(this, null)

        return GeneratorResult()
    }

    override fun visit(document: Document, additionalData: AdditionalData?): GeneratorResult {
        document.pages.forEach { page ->
            page.accept(this, null)
        }

        return GeneratorResult()
    }

    override fun visit(page: Page, additionalData: AdditionalData?): GeneratorResult {
        // Every child of a page will be an entire compose file

        page.frames.forEach { frame ->
            frame.accept(this, null)
        }

        return GeneratorResult()
    }

    override fun visit(rectangleNode: RectangleNode, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult()
    }

    override fun visit(frame: Frame, additionalData: AdditionalData?): GeneratorResult {
        if (frame.componentType == ComponentType.SCREEN_FRAME) {
            currentImports = mutableSetOf(GeneratorHelpers.COMPOSABLE_IMPORT, GeneratorHelpers.COLUMN_IMPORT)
            componentMappings = mutableMapOf()
            val frameName = GeneratorHelpers.generateIdentifier(frame.name)
            mainFileBuilder = FileSpec.builder("", frameName)

            val frameComposableFunction = FunSpec.builder(frameName)
                .addAnnotation(GeneratorHelpers.getComposableAnnotation())
                .beginControlFlow("Column(verticalArrangement=Arrangement.SpaceAround,·horizontalAlignment=Alignment.CenterHorizontally)")

            val sortedGeneratorResults = frame.components
                .map { component -> component.accept(this, null) }
                // Column elements have to be sorted by y
                .sortedBy { result ->
                    if (result.absoluteRenderBounds != null) {
                        return@sortedBy result.absoluteRenderBounds.y
                    } else {
                        return@sortedBy 0.0
                    }
                }

            sortedGeneratorResults.forEach { generatorResult ->
                if (generatorResult.statement != null) {
                    frameComposableFunction.addCode(generatorResult.statement)
                }
            }

            frameComposableFunction.endControlFlow()
            mainFileBuilder.addFunction(frameComposableFunction.build())

            currentImports.forEach { importString -> mainFileBuilder.addImport(importString, "") }
            mainFileBuilder.build().writeTo(File(OUT_DIRECTORY_PATH))
        } else if (frame.componentType.isTag) {
            val codeBlockBuilder = CodeBlock.builder()
            if (frame.componentType == ComponentType.BUTTON) {
                currentImports.add(GeneratorHelpers.MATERIAL_BUTTON_IMPORT)
                codeBlockBuilder.beginControlFlow("Button(onClick = {}, ${generateButtonModifier(frame.absoluteRenderBounds, frame.fills)})")
            }

            frame.components.forEach { component ->
                val generatorResult = component.accept(this, null)
                if (generatorResult.statement != null) {
                    codeBlockBuilder.add(generatorResult.statement)
                }
            }

            if (!codeBlockBuilder.isEmpty()) {
                codeBlockBuilder.endControlFlow()
            }

            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = frame.absoluteRenderBounds)
        } else {
            frame.components.forEach { component ->
                component.accept(this)
            }
        }

        return GeneratorResult(absoluteRenderBounds = frame.absoluteRenderBounds)
    }

    override fun visit(instance: Instance, additionalData: AdditionalData?): GeneratorResult {
        if (instance.componentType.isM3Tag) {
            return M3GeneratorHelpers.generateM3Component(instance, componentDescriptions, currentImports)
        }

        val codeBlockBuilder = CodeBlock.builder()
        if (instance.componentType == ComponentType.TEXT_FIELD) {
            currentImports.add(GeneratorHelpers.MATERIAL_TEXTFIELD_IMPORT)
            val textGeneratorResult = instance.components[0].accept(this, null)
            codeBlockBuilder.add(buildCodeBlock { addStatement("TextField(value=\"\", onValueChange={}, placeholder={·${textGeneratorResult.statement}·}, ${generateModifier(instance)})")})

            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        } else if (instance.componentType == ComponentType.BUTTON) {
            currentImports.add(GeneratorHelpers.MATERIAL_BUTTON_IMPORT)
            codeBlockBuilder.beginControlFlow("Button(onClick = {}, ${generateButtonModifier(instance.absoluteRenderBounds, instance.fills)})")
        } else if (instance.componentType == ComponentType.ROW) {
            currentImports.add(GeneratorHelpers.ROW_IMPORT)
            codeBlockBuilder.beginControlFlow("Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically)")

            instance.components
                .map { component -> component.accept(this, null) }
                // Row elements have to be sorted by x
                .sortedBy { result ->
                    if (result.absoluteRenderBounds != null) {
                        return@sortedBy result.absoluteRenderBounds.x
                    } else {
                        return@sortedBy 0.0
                    }
                }
                .forEach { generatorResult ->
                    if (generatorResult.statement != null) {
                        codeBlockBuilder.add(generatorResult.statement)
                    }
                }

            codeBlockBuilder.endControlFlow()
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        } else {
            if (componentMappings.containsKey(instance.componentId)) {
                val componentFunctionName = componentMappings[instance.componentId]
                return GeneratorResult(statement = buildCodeBlock { addStatement("${componentFunctionName}(${generateModifier(instance)})") }, absoluteRenderBounds = instance.absoluteRenderBounds)
            } else {
                val componentFunctionName = GeneratorHelpers.generateIdentifier(instance.name)
                val colorString = if (instance.fills.isEmpty()) "" else ".background(${getColorModifier(instance.fills[0])})"
                val composableFunction = FunSpec.builder(componentFunctionName)
                        .addAnnotation(GeneratorHelpers.getComposableAnnotation())
                        .addParameter("modifier", getModifierType())
                        .beginControlFlow("Column(modifier=modifier${colorString},·verticalArrangement=Arrangement.SpaceAround,·horizontalAlignment=Alignment.CenterHorizontally)")

                instance.components
                    .map { component -> component.accept(this, null) }
                    // Column elements have to be sorted by y
                    .sortedBy { result ->
                        if (result.absoluteRenderBounds != null) {
                            return@sortedBy result.absoluteRenderBounds.y
                        } else {
                            return@sortedBy 0.0
                        }
                    }
                    .forEach { generatorResult ->
                        if (generatorResult.statement != null) {
                            composableFunction.addStatement(generatorResult.statement.toString())
                        }
                }

                composableFunction.endControlFlow()
                mainFileBuilder.addFunction(composableFunction.build())
                componentMappings[instance.componentId] = componentFunctionName
                return GeneratorResult(statement = buildCodeBlock { addStatement("${componentFunctionName}(${generateModifier(instance)})") }, absoluteRenderBounds = instance.absoluteRenderBounds)
            }
        }

        instance.components.forEach { component ->
            val generatorResult = component.accept(this)
            if (generatorResult.statement != null) {
              codeBlockBuilder.add(generatorResult.statement)
            }
        }

        if (!codeBlockBuilder.isEmpty()) {
            codeBlockBuilder.endControlFlow()
        }

        val generatorStatement = if (!codeBlockBuilder.isEmpty()) codeBlockBuilder.build() else null
        return GeneratorResult(statement = generatorStatement, absoluteRenderBounds = instance.absoluteRenderBounds)
    }

    override fun visit(component: Component, additionalData: AdditionalData?): GeneratorResult {
        val codeBlockBuilder = CodeBlock.builder()
        if (component.componentType == ComponentType.TEXT_FIELD) {
            currentImports.add(GeneratorHelpers.MATERIAL_TEXTFIELD_IMPORT)
            val textGeneratorResult = component.components[0].accept(this, null)
            codeBlockBuilder.add(buildCodeBlock { addStatement("TextField(value=\"\", onValueChange={}, placeholder={·${textGeneratorResult.statement}·}, ${generateModifier(component)})")})

            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = component.absoluteRenderBounds)
        } else if (component.componentType == ComponentType.BUTTON) {
            codeBlockBuilder.beginControlFlow("Button(onClick = {}, ${generateButtonModifier(component.absoluteRenderBounds, component.fills)})")
        } else if (component.componentType == ComponentType.LIST) {
            codeBlockBuilder.beginControlFlow("Column()")
            component.components
                .map { componentChild -> componentChild.accept(this, null) }
                .forEachIndexed { index, generatorResult ->
                    if (generatorResult.statement != null) {
                        repeat(M3GeneratorHelpers.m3ListItemMultiplier) {
                            codeBlockBuilder.add(generatorResult.statement)
                            if (it != M3GeneratorHelpers.m3ListItemMultiplier - 1 || index != component.components.size - 1) {
                                codeBlockBuilder.addStatement("Divider(thickness=${component.componentType.additionalData}.dp)")
                            }
                        }
                    }
            }

            codeBlockBuilder.endControlFlow()
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = component.absoluteRenderBounds)
        } else if (component.componentType == ComponentType.ROW) {
            currentImports.add(GeneratorHelpers.ROW_IMPORT)
            codeBlockBuilder.beginControlFlow("Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceAround,verticalAlignment=Alignment.CenterVertically)")

            component.components
                .map { componentChild -> componentChild.accept(this, null) }
                // Row elements have to be sorted by x
                .sortedBy { result ->
                    if (result.absoluteRenderBounds != null) {
                        return@sortedBy result.absoluteRenderBounds.x
                    } else {
                        return@sortedBy 0.0
                    }
                }
                .forEach { generatorResult ->
                    if (generatorResult.statement != null) {
                        codeBlockBuilder.add(generatorResult.statement)
                    }
                }

            codeBlockBuilder.endControlFlow()
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = component.absoluteRenderBounds)
        } else {
            if (componentMappings.containsKey(component.id)) {
                val componentFunctionName = componentMappings[component.id]
                return GeneratorResult(statement = buildCodeBlock { addStatement("${componentFunctionName}(${generateModifier(component)})") }, absoluteRenderBounds = component.absoluteRenderBounds)
            } else {
                val componentFunctionName = GeneratorHelpers.generateIdentifier(component.name)
                val colorString = if (component.fills.isEmpty()) "" else ".background(${getColorModifier(component.fills[0])})"
                val composableFunction = FunSpec.builder(componentFunctionName)
                    .addAnnotation(GeneratorHelpers.getComposableAnnotation())
                    .addParameter("modifier", getModifierType())
                    .beginControlFlow("Column(modifier=modifier${colorString},·verticalArrangement=Arrangement.SpaceAround,·horizontalAlignment=Alignment.CenterHorizontally)")

                component.components
                    .map { componentChild -> componentChild.accept(this, null) }
                    // Column elements have to be sorted by y
                    .sortedBy { result ->
                        if (result.absoluteRenderBounds != null) {
                            return@sortedBy result.absoluteRenderBounds.y
                        } else {
                            return@sortedBy 0.0
                        }
                    }
                    .forEach { generatorResult ->
                    if (generatorResult.statement != null) {
                        composableFunction.addStatement(generatorResult.statement.toString())
                    }
                }

                composableFunction.endControlFlow()
                mainFileBuilder.addFunction(composableFunction.build())
                componentMappings[component.id] = componentFunctionName
                return GeneratorResult(statement = buildCodeBlock { addStatement("${componentFunctionName}(${generateModifier(component)})") }, absoluteRenderBounds = component.absoluteRenderBounds)
            }
        }

        component.components.forEach { childComponent ->
            val generatorResult = childComponent.accept(this, null)
            if (generatorResult.statement != null) {
                codeBlockBuilder.add(generatorResult.statement)
            }
        }

        if (!codeBlockBuilder.isEmpty()) {
            codeBlockBuilder.endControlFlow()
        }

        val generatorStatement = if (!codeBlockBuilder.isEmpty()) codeBlockBuilder.build() else null
        return GeneratorResult(statement = generatorStatement, absoluteRenderBounds = component.absoluteRenderBounds)
    }

    override fun visit(vector: Vector, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult()
    }

    override fun visit(booleanOperation: BooleanOperation, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult()
    }

    override fun visit(line: Line, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult()
    }

    override fun visit(text: Text, additionalData: AdditionalData?): GeneratorResult {
        currentImports.add(GeneratorHelpers.MATERIAL_TEXT_IMPORT)
        return GeneratorResult(statement = buildCodeBlock { addStatement("Text(${generateModifier(text)})")},
                               absoluteRenderBounds = text.absoluteRenderBounds)
    }
}