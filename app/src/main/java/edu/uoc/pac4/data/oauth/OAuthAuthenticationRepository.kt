package edu.uoc.pac4.data.oauth

import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.datasource.TwitchDataSource

/**
 * Created by alex on 11/21/20.
 */
class OAuthAuthenticationRepository(
    // TODO: Add any datasources you may need
        private val twitchDataSource: TwitchDataSource,
        private val sharedPreferencesDataSource: SessionManager
) : AuthenticationRepository {

    override suspend fun isUserAvailable(): Boolean {
        //TODO("Not yet implemented")
        return sharedPreferencesDataSource.isUserAvailable()
    }

    override suspend fun login(authorizationCode: String): OAuthTokensResponse? {
        //TODO("Not yet implemented")
        return twitchDataSource.login(authorizationCode)
    }

    override fun logout() {
        //TODO("Not yet implemented")
        sharedPreferencesDataSource.clearRefreshToken()
        sharedPreferencesDataSource.clearAccessToken()
    }

    override fun onUnauthorized() {
        sharedPreferencesDataSource.clearAccessToken()
    }

    override suspend fun saveAccessToken(accessToken: String) {
        sharedPreferencesDataSource.saveAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        sharedPreferencesDataSource.saveRefreshToken(refreshToken)
    }
}