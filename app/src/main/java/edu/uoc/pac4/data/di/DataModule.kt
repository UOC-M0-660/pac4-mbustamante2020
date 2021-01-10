package edu.uoc.pac4.data.di

import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.datasource.TwitchDataSource
import edu.uoc.pac4.data.network.Network
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.oauth.OAuthAuthenticationRepository
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.data.streams.TwitchStreamsRepository
import edu.uoc.pac4.data.user.TwitchUserRepository
import edu.uoc.pac4.data.user.UserRepository
import org.koin.dsl.module

/**
 * Created by alex on 11/21/20.
 */

val dataModule = module {
    // TODO: Init your Data Dependencies
    single<AuthenticationRepository> {
        OAuthAuthenticationRepository(
            TwitchDataSource(Network.createHttpClient(get())),
            SessionManager(get())
        ) }
    single<StreamsRepository> {
        TwitchStreamsRepository(
            TwitchDataSource(Network.createHttpClient(get())),
            SessionManager(get())
        ) }
    single<UserRepository> {    TwitchUserRepository(TwitchDataSource(Network.createHttpClient(get()))) }


 /*
 single<AuthenticationRepository> {
        OAuthAuthenticationRepository(
            TwitchDataSource(Network.createHttpClient(get()), SessionManager(get())),
            TwitchDataSource(Network.createHttpClient(get()), SessionManager(get()))
        ) }
    single<StreamsRepository> { TwitchStreamsRepository(TwitchDataSource(Network.createHttpClient(get()), null)) }
    single<UserRepository> {    TwitchUserRepository(TwitchDataSource(Network.createHttpClient(get()), null)) }
*/
    // Streams example
    // single<StreamsRepository> { TwitchStreamsRepository() }
}