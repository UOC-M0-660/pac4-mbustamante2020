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
    viewModel { LaunchViewModel(repository = get()) }
    viewModel { OAuthViewModel(repository = get()) }
    viewModel { StreamsViewModel(repository = get()) }
    viewModel { ProfileViewModel(repository = get()) }

    // LaunchViewModel example
    // viewModel { LaunchViewModel(repository = get()) }
}