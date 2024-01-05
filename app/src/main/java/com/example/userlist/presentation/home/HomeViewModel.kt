package com.example.userlist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.common.Resource
import com.example.userlist.domain.usecase.GetUsersUseCase
import com.example.userlist.presentation.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _usersList = MutableStateFlow<Resource<List<Users>>?>(Resource.Loading(false))
    val getUsersList: StateFlow<Resource<List<Users>>?> = _usersList.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    init {
        getUsersList()
    }

    private fun getUsersList() {
        viewModelScope.launch {
            getUsersUseCase.invoke().collect {
                _usersList.value = it
            }
        }
    }

}

sealed class NavigationEvent() {
    data class NavigateToDetailsPage(val userId: Int) : NavigationEvent()
}