package parser

import data.Result
import data.nodes.root.RootDocument

interface Parser {
    fun parse(input: String): Result<RootDocument>
}