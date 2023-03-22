package data

import data.nodes.NodeType
import data.nodes.TreeNode

data class AdditionalData(
    val parent: TreeNode,
    val parentType: NodeType
)
