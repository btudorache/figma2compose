package analyser

import data.Visitor
import data.nodes.*
import data.nodes.root.RootDocument

// used for testing/reference
class DummyVisitor : Visitor<Unit> {
    var indent = 0

    fun printIndent(str: String?) {
        for (i in 0 until indent) print("    ")
        println(str)
    }

    override fun visit(rootDocument: RootDocument) {
        printIndent("Root document")
        indent++
        rootDocument.document.accept(this);
        indent--
    }

    override fun visit(document: Document) {
        printIndent("Document ${document.name}")
        indent++
        document.pages.forEach { page ->
            page.accept(this)
        }
        indent--
    }

    override fun visit(page: Page) {
        printIndent("Page ${page.name}")
        indent++
        page.frames.forEach { frame ->
            frame.accept(this)
        }
        indent--
    }

    override fun visit(frame: Frame) {
        printIndent("Frame ${frame.name}")
        indent++
        frame.components.forEach { component ->
            component.accept(this)
        }
        indent--
    }

    override fun visit(instance: Instance) {
        printIndent("Instance ${instance.name}")
        indent++
        instance.components.forEach { component ->
            component.accept(this)
        }
        indent--
    }

    override fun visit(text: Text) {
        printIndent("Text: ${text.characters}")
    }
}