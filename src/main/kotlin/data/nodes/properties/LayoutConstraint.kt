package data.nodes.properties

import data.nodes.properties.enums.HorizontalLayoutConstraint
import data.nodes.properties.enums.VerticalLayoutConstraint

data class LayoutConstraint(
    val vertical: VerticalLayoutConstraint,
    val horizontal: HorizontalLayoutConstraint
)
