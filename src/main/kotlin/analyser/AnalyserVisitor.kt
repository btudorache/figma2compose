package analyser

import data.AdditionalData
import data.Visitor
import data.nodes.*
import data.nodes.RootDocument

class AnalyserVisitor : Visitor<AnalyserResult> {
    override fun visit(rootDocument: RootDocument, additionalData: AdditionalData?): AnalyserResult {
        rootDocument.document.accept(this, null)

        return AnalyserResult()
    }

    override fun visit(document: Document, additionalData: AdditionalData?): AnalyserResult {
        document.pages.forEach { page ->
            page.accept(this, null)
        }

        return AnalyserResult()
    }

    override fun visit(page: Page, additionalData: AdditionalData?): AnalyserResult {
        page.frames.forEach { frame ->
            frame.accept(this, AdditionalData(page, page.type))
        }

        return AnalyserResult()
    }

    override fun visit(frame: Frame, additionalData: AdditionalData?): AnalyserResult {
        if (additionalData != null && additionalData.parentType == NodeType.CANVAS) {
            frame.componentType = ComponentType.SCREEN_FRAME
        } else {
            frame.componentType = ComponentType.COMPONENT_FRAME
        }

        frame.components.forEach { component ->
            component.accept(this, null)
        }

        return AnalyserResult()
    }

    override fun visit(instance: Instance, additionalData: AdditionalData?): AnalyserResult {
        instance.components.forEach { component ->
            component.accept(this, null)
        }

        return AnalyserResult()
    }

    override fun visit(component: Component, additionalData: AdditionalData?): AnalyserResult {
        component.components.forEach { childComponent ->
            childComponent.accept(this, null)
        }

        return AnalyserResult()
    }

    override fun visit(vector: Vector, additionalData: AdditionalData?): AnalyserResult {
        return AnalyserResult()
    }

    override fun visit(line: Line, additionalData: AdditionalData?): AnalyserResult {
        return AnalyserResult()
    }

    override fun visit(text: Text, additionalData: AdditionalData?): AnalyserResult {
        return AnalyserResult(ComponentType.TEXT)
    }
}