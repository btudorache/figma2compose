package parser

import data.Result
import data.nodes.RootDocument

interface Parser {
    fun parse(input: String): Result<RootDocument>
}