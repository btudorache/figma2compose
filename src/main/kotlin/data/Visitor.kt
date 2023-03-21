package data

import data.nodes.*
import data.nodes.root.RootDocument

interface Visitor<T> {
    fun visit(rootDocument: RootDocument, additionalData: Any? = null): T

    fun visit(document: Document, additionalData: Any? = null): T

    fun visit(page: Page, additionalData: Any? = null): T

    fun visit(frame: Frame, additionalData: Any? = null): T

    fun visit(instance: Instance, additionalData: Any? = null): T

    fun visit(text: Text, additionalData: Any? = null): T
}