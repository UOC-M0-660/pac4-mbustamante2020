package edu.uoc.pac4.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.user.User
import edu.uoc.pac4.data.user.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
        private val repository: UserRepository
) : ViewModel() {

    val user = MutableLiveData<User?>()

    fun getUserProfile(): MutableLiveData<User?> {
        viewModelScope.launch {
            val user1 = repository.getUser()
            user.postValue(user1)
        }
        return user


        // Retrieve the Twitch User Profile using the API
        /* try {
             twitchApiService.getUser()?.let { user ->
                 // Success :)
                 // Update the UI with the user data
                 setUserInfo(user)
             } ?: run {
                 // Error :(
                 showError(getString(R.string.error_profile))
             }
             // Hide Loading
             progressBar.visibility = GONE
         } catch (t: UnauthorizedException) {
             onUnauthorized()
         }*/
    }


    suspend fun updateUserDescription(description: String): MutableLiveData<User?> {
        user.value = repository.updateUser(description)
        return user

            // Update the Twitch User Description using the API
        /* try {
             twitchApiService.updateUserDescription(description)?.let { user ->
                 // Success :)
                 // Update the UI with the user data
                 setUserInfo(user)
             } ?: run {
                 // Error :(
                 showError(getString(R.string.error_profile))
             }
             // Hide Loading
             progressBar.visibility = GONE
         } catch (t: UnauthorizedException) {
             onUnauthorized()
         }*/
    }



}