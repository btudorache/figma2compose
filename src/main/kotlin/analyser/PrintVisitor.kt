package analyser

import data.AdditionalData
import data.Visitor
import data.nodes.*
import data.nodes.RootDocument

// used for testing/reference
class PrintVisitor : Visitor<Unit> {
    var indent = 0

    private fun printIndent(str: String?) {
        for (i in 0 until indent) print("    ")
        println(str)
    }

    override fun visit(rootDocument: RootDocument, additionalData: AdditionalData?) {
        printIndent("Root document")
        indent++
        rootDocument.document.accept(this, null)
        indent--
    }

    override fun visit(document: Document, additionalData: AdditionalData?) {
        printIndent("Document: ${document.name}")
        indent++
        document.pages.forEach { page ->
            page.accept(this, null)
        }
        indent--
    }

    override fun visit(page: Page, additionalData: AdditionalData?) {
        printIndent("Page: ${page.name}")
        indent++
        page.frames.forEach { frame ->
            frame.accept(this, null)
        }
        indent--
    }

    override fun visit(rectangleNode: RectangleNode, additionalData: AdditionalData?) {
        printIndent("Vector: ${rectangleNode.name}")
    }

    override fun visit(frame: Frame, additionalData: AdditionalData?) {
        printIndent("Frame: ${frame.name}")
        indent++
        frame.components.forEach { component ->
            component.accept(this, null)
        }
        indent--
    }

    override fun visit(instance: Instance, additionalData: AdditionalData?) {
        printIndent("Instance: ${instance.name} with type ${instance.componentType}")
        indent++
        instance.components.forEach { component ->
            component.accept(this, null)
        }
        indent--
    }

    override fun visit(component: Component, additionalData: AdditionalData?) {
        printIndent("Component: ${component.name}")
        indent++
        component.components.forEach { childComponent ->
            childComponent.accept(this, null)
        }
        indent--
    }

    override fun visit(vector: Vector, additionalData: AdditionalData?) {
        printIndent("Vector: ${vector.name}")
    }

    override fun visit(booleanOperation: BooleanOperation, additionalData: AdditionalData?) {
        printIndent("Boolean operation: ${booleanOperation.name} with type: ${booleanOperation.booleanOperation}")
    }

    override fun visit(line: Line, additionalData: AdditionalData?) {
        printIndent("Line: ${line.name}")
    }

    override fun visit(text: Text, additionalData: AdditionalData?) {
        printIndent("Text: ${text.characters}")
    }
}