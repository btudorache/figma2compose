package data.nodes

abstract class Component(
    val id: String = "",
    val name: String = "",
    val type: NodeType = NodeType.UNKNOWN
) : Visitable
