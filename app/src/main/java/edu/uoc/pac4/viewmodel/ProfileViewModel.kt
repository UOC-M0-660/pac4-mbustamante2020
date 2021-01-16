package edu.uoc.pac4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.R
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.user.User
import edu.uoc.pac4.data.user.UserRepository
import edu.uoc.pac4.utils.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(
        private val repository: UserRepository,
        private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _user = MutableLiveData<Resource<User?>>()
    val user: LiveData<Resource<User?>>
        get() = _user

    fun getUserProfile() {
        viewModelScope.launch {
            _user.postValue(Resource.loading(null))

            repository.getUser()?.let { response ->
                // Success :)
                _user.postValue(Resource.success(response))
            } ?: run {
                // Failure :(
                _user.postValue(Resource.error(R.string.error_profile.toString(), null))
            }
        }
    }

    fun updateUserDescription(description: String) {
        viewModelScope.launch {
            _user.postValue(Resource.loading(null))

            repository.updateUser(description)?.let { response ->
                // Success :)
                _user.postValue(Resource.success(response))
            } ?: run {
                // Failure :(
                _user.postValue(Resource.error(R.string.error_profile.toString(), null))
            }
        }
    }

    fun logout() {
        authenticationRepository.logout()
    }

    fun onUnauthorized() {
        authenticationRepository.onUnauthorized()
    }
}