package analyser

import data.AdditionalData
import data.Visitor
import data.nodes.*
import data.nodes.RootDocument

class AnalyserVisitor : Visitor<AnalyserResult> {
    val errorMessages = arrayOf<String>()
    val warningsMessages = arrayOf<String>()

    /**
     * Semantic check between parent type and children types.
     * Returns False if there are errors
     */
    private fun checkSemantics(parentType: ComponentType, childrenTypes: List<ComponentType>): Boolean {
        // TODO: check semantics for various types and their children
        return true
    }

    private fun checkM3Semantics(parentType: ComponentType): Boolean {
        // TODO: check semantics for various M3 components
        return true
    }

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
        instance.componentType = ComponentType.findTaggedComponentType(instance.name)
        if (instance.componentType.isM3Tag) {
            checkM3Semantics(instance.componentType)
            // return here probably
        }

        instance.components.forEach { component ->
            component.accept(this, null)
        }

        return AnalyserResult()
    }

    override fun visit(component: Component, additionalData: AdditionalData?): AnalyserResult {
        component.componentType = ComponentType.findTaggedComponentType(component.name)


        val childComponentResults = component.components.map { childComponent ->
            childComponent.accept(this, null)
        }

        checkSemantics(component.componentType, childComponentResults.map { result -> result.componentType })

        return AnalyserResult(component.componentType)
    }

    override fun visit(vector: Vector, additionalData: AdditionalData?): AnalyserResult {
        return AnalyserResult()
    }

    override fun visit(line: Line, additionalData: AdditionalData?): AnalyserResult {
        return AnalyserResult()
    }

    override fun visit(text: Text, additionalData: AdditionalData?): AnalyserResult {
        text.componentType = ComponentType.TEXT
        return AnalyserResult(text.componentType)
    }
}