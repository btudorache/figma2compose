package data.nodes

import data.nodes.enums.NodeType

// TODO: this will become an abstract class probably
data class Component(
    val id: String,
    val name: String,
    val type: NodeType
)
