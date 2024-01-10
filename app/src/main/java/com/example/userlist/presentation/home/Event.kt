package com.example.userlist.presentation.home

import com.example.userlist.presentation.model.Users

sealed class OnEvent() {
    data class Listener(val userId: Int) : OnEvent()
    data class Filter(val userId: MutableList<Int>) : OnEvent()
    data class IsSelect(val users: Users) : OnEvent()
    data class IsUnSelect(val users: Users) : OnEvent()
    data object DeleteUsers : OnEvent()

}

sealed class NavigationEvent() {
    data class NavigateToCurrentUser(val userId: Int) : NavigationEvent()
}