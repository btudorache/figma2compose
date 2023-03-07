package contracts

data class Result<T>(
    val hasError: Boolean,
    val data: T? = null,
    val errorMessage: String? = null
) {
    companion object {
        fun <T> success(data: T): Result<T> = Result(false, data)

        fun <T> failure(errorMessage: String): Result<T> = Result(true, null, errorMessage)
    }
}
