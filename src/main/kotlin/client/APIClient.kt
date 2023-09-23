package client

import data.Result

interface APIClient {
    fun loadFromApi(fileId: String, apiToken: String): Result<String>
}