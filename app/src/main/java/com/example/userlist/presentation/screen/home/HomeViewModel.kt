package com.example.userlist.presentation.screen.home

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

    private val _usersList = MutableStateFlow<List<Users>>(emptyList())
    val usersList: StateFlow<List<Users>> = _usersList.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        getUsersList()
    }

    fun onEvent(event: OnEvent) {
        when (event) {
            is OnEvent.Listener -> getListener(id = event.userId)
            is OnEvent.CleanData -> getUsersList()
            is OnEvent.IsSelect -> getUserForDeleteCheck(users = event.users)
            is OnEvent.IsUnSelect -> getUserForDeleteUncheck(users = event.users)
            is OnEvent.DeleteUsers -> deleteUser()
        }
    }

    private fun getUsersList() {
        viewModelScope.launch {
            getUsersUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _usersList.value = resource.data.map { it.copy() }
                        _loading.value = false
                    }

                    is Resource.Error -> {
                        _usersList.value = _usersList.value.map { it.copy() }
                        _errorMessage.value = resource.errorMessage
                        _loading.value = false
                    }

                    is Resource.Loading -> {
                        _loading.value = true
                    }
                }
            }
        }
    }

    private fun deleteUser() {
        _usersList.value = _usersList.value.filterNot { user ->
            user.isSelect
        }
    }

    private fun getUserForDeleteCheck(users: Users) {
        _usersList.value = _usersList.value.map {
            if (it.id == users.id) it.copy(isSelect = true) else it
        }
    }

    private fun getUserForDeleteUncheck(users: Users) {
        _usersList.value = _usersList.value.map {
            if (it.id == users.id) it.copy(isSelect = false) else it
        }
    }

    private fun getListener(id: Int) {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToCurrentUser(userId = id))
        }
    }

}