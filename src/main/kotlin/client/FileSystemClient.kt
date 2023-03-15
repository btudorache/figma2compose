package client

import data.Result


interface FileSystemClient {
    fun loadFromFileStorage(path: String): Result<String>
}