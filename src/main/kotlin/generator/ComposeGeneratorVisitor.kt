package generator

import data.Visitor
import data.nodes.*
import data.nodes.root.RootDocument

class ComposeGeneratorVisitor : Visitor<GeneratorResult> {
    override fun visit(rootDocument: RootDocument, additionalData: Any?): GeneratorResult {

        rootDocument.document.accept(this, additionalData);

        return GeneratorResult()
    }

    override fun visit(document: Document, additionalData: Any?): GeneratorResult {
        document.pages.forEach { page ->
            page.accept(this, additionalData)
        }

        return GeneratorResult()
    }

    override fun visit(page: Page, additionalData: Any?): GeneratorResult {
        // Every child of a page will be an entire compose file

        page.frames.forEach { frame ->
            frame.accept(this, additionalData)
        }

        return GeneratorResult()
    }

    override fun visit(frame: Frame, additionalData: Any?): GeneratorResult {
        frame.components.forEach { component ->
            component.accept(this, additionalData)
        }

        return GeneratorResult()
    }

    override fun visit(instance: Instance, additionalData: Any?): GeneratorResult {
        instance.components.forEach { component ->
            component.accept(this, additionalData)
        }

        return GeneratorResult()
    }

    override fun visit(text: Text, additionalData: Any?): GeneratorResult {
        return GeneratorResult()
    }
}