package client

import contracts.Result

interface APIClient {
    fun loadFromApi(fileId: String): Result<String>
}