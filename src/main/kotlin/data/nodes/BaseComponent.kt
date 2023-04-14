package data.nodes

abstract class BaseComponent(
    val id: String = "",
    var name: String = "",
    val type: NodeType = NodeType.UNKNOWN
) : TreeNode()
