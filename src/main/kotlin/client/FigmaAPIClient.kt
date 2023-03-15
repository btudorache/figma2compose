package client

import data.Result
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

private enum class HttpStatusCode(val code: Int) {
    OK(200)
}

class FigmaAPIClient(
    private val httpClient: HttpClient = HttpClient.newBuilder().build()
) : APIClient {
    private val FIGMA_FILES_ENDPOINT = "https://api.figma.com/v1/files"
    private val FIGMA_TOKEN_HEADER = "X-Figma-Token"
    // TODO: fetch API token from config/env
    private val figmaApiToken = "figd_HUMiSMq837EX_1Fm7iCO90V0j1NI2yU7gFhUGH4K"

    override fun loadFromApi(fileId: String): Result<String> {
        try {
            val request = HttpRequest.newBuilder()
                .uri(URI.create("${FIGMA_FILES_ENDPOINT}/${fileId}"))
                .header(FIGMA_TOKEN_HEADER, figmaApiToken)
                .build()

            val response = httpClient.send(request, BodyHandlers.ofString())
            if (response.statusCode() != HttpStatusCode.OK.code) {
                return data.Result.failure("Failed to fetch figma data file for $fileId: ${response.body()}")
            }

            return data.Result.success(response.body())
        } catch (e: Exception) {
            return data.Result.failure("http client send failure: ${e.message}")
        }
    }
}