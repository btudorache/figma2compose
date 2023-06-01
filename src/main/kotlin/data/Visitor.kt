package data

import data.nodes.*
import data.nodes.RootDocument

interface Visitor<T> {
    fun visit(rootDocument: RootDocument, additionalData: AdditionalData? = null): T

    fun visit(document: Document, additionalData: AdditionalData? = null): T

    fun visit(page: Page, additionalData: AdditionalData? = null): T

    fun visit(rectangleNode: RectangleNode, additionalData: AdditionalData? = null): T

    fun visit(frame: Frame, additionalData: AdditionalData? = null): T

    fun visit(instance: Instance, additionalData: AdditionalData? = null): T

    fun visit(component: Component, additionalData: AdditionalData? = null): T

    fun visit(vector: Vector, additionalData: AdditionalData? = null): T

    fun visit(booleanOperation: BooleanOperation, additionalData: AdditionalData? = null): T

    fun visit(line: Line, additionalData: AdditionalData? = null): T

    fun visit(text: Text, additionalData: AdditionalData? = null): T
}