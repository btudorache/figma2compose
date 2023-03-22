package data.nodes

abstract class BaseComponent(
    val id: String = "",
    val name: String = "",
    val type: NodeType = NodeType.UNKNOWN
) : TreeNode()
