package edu.uoc.pac4.data.user

import edu.uoc.pac4.data.datasource.TwitchDataSource

/**
 * Created by alex on 11/21/20.
 */

class TwitchUserRepository(
    // TODO: Add any datasources you may need
    private val twitchDataSource: TwitchDataSource
) : UserRepository {

    override suspend fun getUser(): User? {
        //TODO("Not yet implemented")
        return twitchDataSource.getUser()
    }

    override suspend fun updateUser(description: String): User? {
        //TODO("Not yet implemented")
        return twitchDataSource.updateUser(description)
    }
}