package data.nodes

import analyser.ComponentType
import com.google.gson.annotations.Expose
import data.Visitable

abstract class TreeNode(
    @Expose(serialize = false, deserialize = false)
    var componentType: ComponentType = ComponentType.UNKNOWN
) : Visitable