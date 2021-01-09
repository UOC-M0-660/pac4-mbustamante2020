package edu.uoc.pac4.data.oauth

/**
 * Created by alex on 12/09/2020.
 */

interface AuthenticationRepository {

    suspend fun isUserAvailable(): Boolean

    /// Returns true if the user logged in successfully. False otherwise
    suspend fun login(authorizationCode: String): OAuthTokensResponse?

    suspend fun logout()

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)
}