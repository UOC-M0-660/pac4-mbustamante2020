package edu.uoc.pac4.data.datasource

import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.TwitchApiService
import edu.uoc.pac4.data.oauth.OAuthTokensResponse
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsResponse
import edu.uoc.pac4.data.user.User
import io.ktor.client.*

class TwitchDataSource(
    httpClient: HttpClient,
    private val sessionManager: SessionManager?
)  {
    private val TAG = "TwitchDataSource"
    private val service = TwitchApiService(httpClient)

    suspend fun login(authorizationCode: String): OAuthTokensResponse? {
        return service.getTokens(authorizationCode)
    }

    suspend fun isUserAvailable(): Boolean {
        if (sessionManager != null) {
            return sessionManager.isUserAvailable()
        }
        return false
    }

    suspend fun logout() {
        sessionManager?.clearAccessToken()
    }

    suspend fun saveAccessToken(accessToken: String) {
        sessionManager?.saveAccessToken(accessToken)
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        sessionManager?.saveRefreshToken(refreshToken)
    }

    suspend fun getUser(): User? {
        return service.getUser()
    }

    suspend fun updateUser(description: String): User? {
        return service.updateUserDescription(description)
    }

    /// Gets Streams on Twitch
    suspend fun getStreams(cursor: String?): Pair<String?, List<Stream>?> {
        val streamsResponse: StreamsResponse? = service.getStreams(cursor)
        return Pair(streamsResponse?.pagination?.cursor, streamsResponse?.data)
    }
}