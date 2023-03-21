package analyser

import data.Visitor
import data.nodes.*
import data.nodes.root.RootDocument

// used for testing/reference
class DummyVisitor : Visitor<AnalyserResult> {
    var indent = 0

    private fun printIndent(str: String?) {
        for (i in 0 until indent) print("    ")
        println(str)
    }

    override fun visit(rootDocument: RootDocument, additionalData: Any?): AnalyserResult {
        printIndent("Root document")
        indent++
        rootDocument.document.accept(this, additionalData);
        indent--

        return AnalyserResult()
    }

    override fun visit(document: Document, additionalData: Any?): AnalyserResult {
        printIndent("Document ${document.name}")
        indent++
        document.pages.forEach { page ->
            page.accept(this, additionalData)
        }
        indent--

        return AnalyserResult()
    }

    override fun visit(page: Page, additionalData: Any?): AnalyserResult {
        printIndent("Page ${page.name}")
        indent++
        page.frames.forEach { frame ->
            frame.accept(this, additionalData)
        }
        indent--

        return AnalyserResult()
    }

    override fun visit(frame: Frame, additionalData: Any?): AnalyserResult {
        printIndent("Frame ${frame.name}")
        indent++
        frame.components.forEach { component ->
            component.accept(this, additionalData)
        }
        indent--

        return AnalyserResult()
    }

    override fun visit(instance: Instance, additionalData: Any?): AnalyserResult {
        printIndent("Instance ${instance.name}")
        indent++
        instance.components.forEach { component ->
            component.accept(this, additionalData)
        }
        indent--

        return AnalyserResult()
    }

    override fun visit(text: Text, additionalData: Any?): AnalyserResult {
        printIndent("Text: ${text.characters}")

        return AnalyserResult()
    }
}