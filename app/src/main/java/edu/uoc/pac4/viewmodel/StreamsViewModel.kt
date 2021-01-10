package edu.uoc.pac4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uoc.pac4.R
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.utils.Resource


class StreamsViewModel(
        private val repository: StreamsRepository,
        private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _streams = MutableLiveData<Resource<Pair<String?, List<Stream>?>>>()
    val streams: LiveData<Resource<Pair<String?, List<Stream>?>>>
        get() = _streams

    suspend fun getAllStreams(cursor: String?) {
        _streams.postValue(Resource.loading(null))

         repository.getStreams(cursor).let { streams ->

             streams.second?.let {
                 // Success :)
                 _streams.postValue(Resource.success(streams))

             } ?: run {
             // Failure :(
                _streams.postValue(Resource.error(R.string.error_streams.toString(), null))

            }
         }
    }

    fun onUnauthorized() {
        authenticationRepository.onUnauthorized()
    }
}