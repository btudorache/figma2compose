package data.nodes

import data.Visitable

abstract class Component(
    val id: String = "",
    val name: String = "",
    val type: NodeType = NodeType.UNKNOWN
) : Visitable
