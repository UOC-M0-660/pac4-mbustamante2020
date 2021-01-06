package edu.uoc.pac4.data.oauth

import edu.uoc.pac4.data.datasource.TwitchDataSource

/**
 * Created by alex on 11/21/20.
 */
class OAuthAuthenticationRepository(
    // TODO: Add any datasources you may need
        private val twitchDataSource: TwitchDataSource
) : AuthenticationRepository {

    override suspend fun isUserAvailable(): Boolean {
        //TODO("Not yet implemented")
        return twitchDataSource.isUserAvailable()
    }

    override suspend fun login(authorizationCode: String): OAuthTokensResponse? {
        //TODO("Not yet implemented")
        return twitchDataSource.login(authorizationCode)
    }

    override suspend fun logout() {
        //TODO("Not yet implemented")
        twitchDataSource.logout()
    }

    override fun saveAccessToken(accessToken: String) {
        twitchDataSource.saveAccessToken(accessToken)
    }

    override fun saveRefreshToken(refreshToken: String) {
        twitchDataSource.saveRefreshToken(refreshToken)
    }
}