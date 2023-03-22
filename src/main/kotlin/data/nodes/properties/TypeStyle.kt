package data.nodes.properties

import data.nodes.enums.*

data class TypeStyle(
    val fontFamily: String,
    val fontPostScriptName: String,
    val paragraphSpacing: Int,
    val paragraphIndent: Int,
    val listSpacing: Int,
    val italic: Boolean,
    val fontWeight: Int,
    val fontSize: Int,
    val textCase: TextCase,
    val textDecoration: TextDecoration,
    val textAutoResize: TextAutoResize,
    val textAlignHorizontal: TextAlignHorizontal,
    val textAlignVertical: TextAlignVertical,
    val letterSpacing: Int,
    val fills: Array<Paint>,
    val lineHeightPx: Int,
    val lineHeightPercent: Int,
    val lineHeightPercentFontSize: Int,
    val lineHeightUnit: String
)
