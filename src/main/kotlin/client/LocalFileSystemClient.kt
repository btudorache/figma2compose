package client

import data.Result
import java.io.File

class LocalFileSystemClient : FileSystemClient {
    override fun loadFromFileStorage(path: String): Result<String> {
        val figmaFile = File(path)
        if (!figmaFile.isFile) {
            return Result.failure("Failed to open file $path")
        }

        return Result.success(figmaFile.readText())
    }
}