package com.example.prefdatastorepractice.core.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prefdatastorepractice.core.repositories.PrefRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PrefViewModel(
    private val datastore: PrefRepositoryImpl
): ViewModel() {

    var isDarkMode = datastore.getBoolean("app_dark_mode", false).stateIn(
        viewModelScope, started = SharingStarted.Eagerly, initialValue = false
    )

    var isOnBoardingComplete = datastore.getBoolean("onBoardingComplete", false).stateIn(
        viewModelScope, started = SharingStarted.Eagerly, initialValue = false
    )
    fun isOnBoardingComplete(){
        viewModelScope.launch {
            datastore.saveBoolean("onBoardingComplete", true)
        }
    }
    fun setTheme(isDark: Boolean){
        viewModelScope.launch {
            datastore.saveBoolean("app_dark_mode", isDark)
        }
    }

    val userData = combine(
        datastore.getString("user_data", "Guest"),
        datastore.getLong("user_id", 0L),
        datastore.getString("auth_token", "no token"),
    ){user, id, token->
        UserData(name = user, id = id, token = token)
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = UserData("Loading", 0L, "no token")
    )
    fun saveUserData(name: String, id: Long, token: String){
        viewModelScope.launch {
            datastore.saveString(key = "user_data", name)
            datastore.saveLong(key = "user_id", id)
            datastore.saveString(key = "auth_token", token)
        }
    }
}



data class UserData(
    val name: String,
    val id: Long,
    val token: String
)