package client

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import contracts.Result as Result

private enum class HttpStatusCode(val code: Int) {
    OK(200)
}

class FigmaClient(
    private val httpClient: HttpClient = HttpClient.newBuilder().build()
) {
    private val FIGMA_FILES_ENDPOINT = "https://api.figma.com/v1/files"
    private val FIGMA_TOKEN_HEADER = "X-Figma-Token"
    // TODO: fetch token from env variable
    private val figmaApiToken = "figd_HUMiSMq837EX_1Fm7iCO90V0j1NI2yU7gFhUGH4K"

    fun fetchFigmaFile(figmaFileId: String): Result<String> {
        try {
            val request = HttpRequest.newBuilder()
                .uri(URI.create("${FIGMA_FILES_ENDPOINT}/${figmaFileId}"))
                .header(FIGMA_TOKEN_HEADER, figmaApiToken)
                .build()
            val response = httpClient.send(request, BodyHandlers.ofString())
            if (response.statusCode() != HttpStatusCode.OK.code) {
                return Result.failure("Failed to fetch figma data file for $figmaFileId: ${response.body()}")
            }

            return Result.success(response.body())
        } catch (e: Exception) {
            println(e.localizedMessage)
            return Result.failure("http client send failure: ${e.message}")
        }
    }
}