package edu.uoc.pac4.data.streams

import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.datasource.TwitchDataSource

/**
 * Created by alex on 11/21/20.
 */

class TwitchStreamsRepository(
    // TODO: Add any datasources you may need
    private val twitchDataSource: TwitchDataSource,
    private val sharedPreferencesDataSource: SessionManager
) : StreamsRepository {

    override suspend fun getStreams(cursor: String?): Pair<String?, List<Stream>?> {
        //TODO("Not yet implemented")
        return twitchDataSource.getStreams(cursor)
    }

    override suspend fun onUnauthorized() {
        sharedPreferencesDataSource.clearAccessToken()
    }
}