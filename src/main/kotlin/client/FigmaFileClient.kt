package client

class FigmaFileClient(
    private val apiClient: APIClient = FigmaAPIClient(),
    private val fileSystemClient: FileSystemClient = LocalFileSystemClient()
) : APIClient by apiClient, FileSystemClient by fileSystemClient