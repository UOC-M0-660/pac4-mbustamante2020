package edu.uoc.pac4.viewmodel

import androidx.lifecycle.*
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsRepository
import kotlinx.coroutines.launch


class StreamsViewModel(
        private val repository: StreamsRepository
) : ViewModel() {



    //private val response = MutableLiveData<StreamsResponse>()
    //private val allStreams = MediatorLiveData<List<Stream>>()
    val pagination = MediatorLiveData<String>()
/*
    init {
        //getAllMovies()
    }
*/
/*
    fun getSavedMovies() = allStreams
    private fun getAllMovies() {
       viewModelScope.launch {
           val response = repository.getStreams(pagination.toString())
           allStreams.postValue(response.second)
           pagination.postValue(response.first)
        }
    }*/

    fun getCursor() = pagination

    //1
    fun getAllStreams(cursor: String?): LiveData<List<Stream>> {
        //2
        val liveData = MutableLiveData<List<Stream>>()
        viewModelScope.launch {
            val streams = repository.getStreams(cursor)

            //3
            liveData.postValue(streams.second)
            pagination.postValue(streams.first)
        }
        //4
        return liveData
    }

/*
    //1
    private fun fetchMovieByQuery(query: String): LiveData<List<Stream>> {
        //2
        val liveData = MutableLiveData<List<Stream>>()
        viewModelScope.launch(Dispatchers.IO) {
            val streams = repository.getStreams(query)

            //3
            liveData.postValue(streams.second)
        }
        //4
        return liveData
    }
*/
    //fun deleteSavedMovies(movie: Stream) {
    //    repository.deleteMovie(movie)
    //}



   /* private val data = MutableLiveData<List<Stream>>()

    fun getAllStreams(): LiveData<List<Stream>> = data

    fun loadAllStreams() {

    }*/


    /*private val _movieModels = MutableLiveData<Resource<List<Stream>>>()

    val movieModels: LiveData<Resource<List<Stream>>>
        get() = _movieModels

    fun load() {
        invokeGetMovies(false)
    }

    fun refresh() {
        invokeGetMovies(true)
    }

    private fun invokeGetMovies(refresh: Boolean) {
        _movieModels.value = Resource.loading()
        executor(
                interactor = getMovies,
                request = GetMovies.Request(refresh),
                onError = {
                    _movieModels.value = Resource.error(it)
                },
                onSuccess = {
                    _movieModels.value =
                            Resource.success(movieModelFactory.createMovieModels(it))
                }
        )
    }*/





/*
    private val response = MutableLiveData<StreamsResponse>()
    public val listStreams = MutableLiveData<List<Stream>>()
    private val pagination = MutableLiveData<String>()

    init {
        getAllStreams()
    }

    private fun getAllStreams() {
        viewModelScope.launch {
            response.postValue(repository.getStreams(pagination.toString()))
        }
    }

    private fun <T> MutableLiveData<T>.postValue(streams: Pair<String?, List<Stream>>) {
        listStreams.postValue(streams.second)
        pagination.postValue(streams.first)
    }

    */
}



