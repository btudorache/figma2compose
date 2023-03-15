package converter

import data.Result

interface Converter {
    fun convert(input: String): Result<ConverterResult>
}