package data

import data.nodes.*
import data.nodes.root.RootDocument

interface Visitor<T> {
    fun visit(rootDocument: RootDocument): T

    fun visit(document: Document): T

    fun visit(page: Page): T

    fun visit(frame: Frame): T

    fun visit(instance: Instance): T

    fun visit(text: Text): T
}