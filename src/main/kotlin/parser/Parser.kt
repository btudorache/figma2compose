package parser

import contracts.Result
import data.RootDocument

interface Parser {
    fun parse(input: String): Result<RootDocument>
}