package client

import contracts.Result

interface FileSystemClient {
    fun loadFromFileStorage(path: String): Result<String>
}