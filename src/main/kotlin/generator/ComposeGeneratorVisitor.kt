package generator

import analyser.ComponentType
import com.squareup.kotlinpoet.*
import data.AdditionalData
import data.Visitor
import data.nodes.*
import data.nodes.RootDocument
import java.io.File

class ComposeGeneratorVisitor : Visitor<GeneratorResult> {
    private val OUT_DIRECTORY_PATH = "./compose_out/"
    override fun visit(rootDocument: RootDocument, additionalData: AdditionalData?): GeneratorResult {

        rootDocument.document.accept(this, null);

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
            val frameName = frame.name.split(" ").joinToString("_")
            val file = FileSpec.builder("", frameName)

            val frameComposableFunction = FunSpec.builder(frameName)
                .addAnnotation(Helpers.getComposableAnnotation())
                // TODO: specify parameters for column
                .beginControlFlow("Column()")

            frame.components.forEach { component ->
                val generatorResult = component.accept(this, null)
                if (generatorResult.statement != null) {
                    frameComposableFunction.addCode(generatorResult.statement)
                }
            }

            frameComposableFunction.endControlFlow()
            file.addFunction(frameComposableFunction.build())
            file.build().writeTo(File(OUT_DIRECTORY_PATH))
        } else if (frame.componentType == ComponentType.COMPONENT_FRAME) {
            frame.components.forEach { component ->
                component.accept(this, null)
            }
        }

        return GeneratorResult()
    }

    override fun visit(instance: Instance, additionalData: AdditionalData?): GeneratorResult {
        val codeBlockBuilder = CodeBlock.builder()
        if (instance.componentType == ComponentType.BUTTON) {
            codeBlockBuilder.beginControlFlow("Button()")
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
        return GeneratorResult(statement = generatorStatement)
    }

    override fun visit(component: Component, additionalData: AdditionalData?): GeneratorResult {
        val codeBlockBuilder = CodeBlock.builder()
        if (component.componentType == ComponentType.BUTTON) {
            codeBlockBuilder.beginControlFlow("Button()")
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
        return GeneratorResult(statement = generatorStatement)
    }

    override fun visit(vector: Vector, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult()
    }

    override fun visit(line: Line, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult()
    }

    override fun visit(text: Text, additionalData: AdditionalData?): GeneratorResult {
        return GeneratorResult(statement = buildCodeBlock {addStatement("Text(text = \"${text.characters}\")")})
    }
}