package generator

import analyser.ComponentType
import com.squareup.kotlinpoet.*
import data.AdditionalData
import data.Visitor
import data.nodes.*
import data.nodes.RootDocument
import data.nodes.properties.root.RootComponentDescription
import java.io.File

class ComposeGeneratorVisitor : Visitor<GeneratorResult> {
    private val OUT_DIRECTORY_PATH = "./compose_out/"
    private lateinit var componentDescriptions: Map<String, RootComponentDescription>
    // TODO: decide if will keep the import
    private lateinit var currentImports: MutableSet<String>
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

    override fun visit(frame: Frame, additionalData: AdditionalData?): GeneratorResult {
        if (frame.componentType == ComponentType.SCREEN_FRAME) {
            currentImports = mutableSetOf("androidx.compose.runtime.Composable")
            val frameName = frame.name.split(" ").joinToString("_")
            // TODO: add filespec builder as instance variable, because you could add more functions to the file while traversing the tree
            val fileBuilder = FileSpec.builder("", frameName)

            val frameComposableFunction = FunSpec.builder(frameName)
                .addAnnotation(Helpers.getComposableAnnotation())
                // TODO: specify parameters for column
                .beginControlFlow("Column()")

            val sortedGeneratorResults = frame.components
                .map { component -> component.accept(this, null) }
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
            fileBuilder.addFunction(frameComposableFunction.build())

            currentImports.forEach { importString -> fileBuilder.addImport(importString, "") }
            fileBuilder.build().writeTo(File(OUT_DIRECTORY_PATH))
        } else if (frame.componentType == ComponentType.COMPONENT_FRAME) {
            frame.components.forEach { component ->
                component.accept(this, null)
            }
        }

        return GeneratorResult(absoluteRenderBounds = frame.absoluteRenderBounds)
    }

    override fun visit(instance: Instance, additionalData: AdditionalData?): GeneratorResult {
        val codeBlockBuilder = CodeBlock.builder()
        if (instance.componentType == ComponentType.BUTTON) {
            currentImports.add("androidx.compose.material.Button")
            codeBlockBuilder.beginControlFlow("Button(onClick = {})")
        } else if (instance.componentType == ComponentType.M3_BUTTON) {
            currentImports.add("androidx.compose.material3.Button")
            // here should check componentDescriptions to see the m3 button type; and other attributes
            codeBlockBuilder.beginControlFlow("Button(onClick = {})")
            // you can be sure that the following casts are correct; they would have failed in the analysis layer otherwise
            val stateLayerFrame = instance.components[0] as Frame
            val textNode = stateLayerFrame.components[0] as Text
            codeBlockBuilder.add(buildCodeBlock { addStatement("Text(text = \"${textNode.characters}\")") })
            codeBlockBuilder.endControlFlow()
            return GeneratorResult(statement = codeBlockBuilder.build(), absoluteRenderBounds = instance.absoluteRenderBounds)
        }

        instance.components.forEach { component ->
            val generatorResult = component.accept(this, null)
            if (generatorResult.statement != null) {
              codeBlockBuilder.add(generatorResult.statement)
            }
        }

        // TODO: this condition will be changed probably
        if (instance.componentType == ComponentType.BUTTON) {
            codeBlockBuilder.endControlFlow()
        }
        val generatorStatement = if (!codeBlockBuilder.isEmpty()) codeBlockBuilder.build() else null
        return GeneratorResult(statement = generatorStatement, absoluteRenderBounds = instance.absoluteRenderBounds)
    }

    override fun visit(component: Component, additionalData: AdditionalData?): GeneratorResult {
        val codeBlockBuilder = CodeBlock.builder()
        if (component.componentType == ComponentType.BUTTON) {
            codeBlockBuilder.beginControlFlow("Button((onClick = {}))")
        }

        component.components.forEach { childComponent ->
            val generatorResult = childComponent.accept(this, null)
            if (generatorResult.statement != null) {
                codeBlockBuilder.add(generatorResult.statement)
            }
        }

        // TODO: this condition will be changed probably
        if (component.componentType == ComponentType.BUTTON) {
            codeBlockBuilder.endControlFlow()
        }
        val generatorStatement = if (!codeBlockBuilder.isEmpty()) codeBlockBuilder.build() else null
        return GeneratorResult(statement = generatorStatement, absoluteRenderBounds = component.absoluteRenderBounds)
    }

    override fun visit(vector: Vector, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult()
    }

    override fun visit(line: Line, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult()
    }

    override fun visit(text: Text, additionalData: AdditionalData?): GeneratorResult {
        currentImports.add("androidx.compose.material.Text")
        return GeneratorResult(statement = buildCodeBlock {addStatement("Text(text = \"${text.characters}\")")},
                               absoluteRenderBounds = text.absoluteRenderBounds)
    }
}