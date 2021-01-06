package edu.uoc.pac4.ui.di

import edu.uoc.pac4.ui.LaunchViewModel
import edu.uoc.pac4.viewmodel.OAuthViewModel
import edu.uoc.pac4.viewmodel.ProfileViewModel
import edu.uoc.pac4.viewmodel.StreamsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by alex on 11/21/20.
 */

val uiModule = module {
    // TODO: Init your UI Dependencies
/*
    // The Retrofit service using our custom HTTP client instance as a singleton
    single {
        createWebService<SportNewsInterface>(
                okHttpClient = createHttpClient(),
                factory = RxJava2CallAdapterFactory.create(),
                baseUrl = BASE_URL
        )
    }
    // Tells Koin how to create an instance of CatRepository
    factory<NewsRepository> { (NewsRepositoryImpl(sportNewsInterface = get())) }

    //Tells Koin how to create SportNewsInterface
    factory<SportNewsInterface> { (SportNewsInterfaceImpl()) }

    // Specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel { MainViewModel (newsRepository = get ())  }
*/

    //factory<AuthenticationRepository> { (OAuthAuthenticationRepository()) }


    viewModel { LaunchViewModel(repository = get()) }
    viewModel { OAuthViewModel(repository = get()) }
    viewModel { StreamsViewModel(repository = get()) }
    viewModel { ProfileViewModel(repository = get()) }

    //viewModel { StreamsViewModel(edu.uoc.pac4.data.streams.StreamsRepository = get()) }

    // LaunchViewModel example
    // viewModel { LaunchViewModel(repository = get()) }
}