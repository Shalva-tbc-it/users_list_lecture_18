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

//    private val _usersList = MutableStateFlow<Resource<List<Users>>?>(Resource.Loading())
//    val UsersList: StateFlow<Resource<List<Users>>?> = _usersList.asStateFlow()

    private val _usersList = MutableStateFlow<List<Users>>(emptyList())
    val usersList: StateFlow<List<Users>> = _usersList.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

//    private val _excludedUserIds = MutableStateFlow<List<Int>>(emptyList())
//    val excludedUserIds: StateFlow<List<Int>> = _excludedUserIds.asStateFlow()


    init {
        getUsersList()
    }

    fun onEvent(event: OnEvent) {
        when(event) {
            is OnEvent.Listener -> getListener(id = event.userId)
            is OnEvent.Filter -> getUsersList()
            is OnEvent.IsSelect -> getUserForDeleteCheck(users = event.users)
            is OnEvent.IsUnSelect -> getUserForDeleteUncheck(users = event.users)
            is OnEvent.DeleteUsers -> deleteUser()
        }
    }

    private fun getUsersList() {
        viewModelScope.launch {
            getUsersUseCase.invoke().collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        _usersList.value = resource.data.map { it.copy(errorStatus = Users.Status.SUCCESS) }
                        _loading.value = false
                    }
                    is Resource.Error -> {
                        _usersList.value = _usersList.value.map { it.copy(errorStatus = Users.Status.ERROR) }
                        _loading.value = false
                    }
                    is Resource.Loading -> {
                        _loading.value = true
                    }
                }
            }
//            getUsersUseCase.invoke().map { resource ->
//                if (resource is Resource.Success) {
//                    resource.copy(data = resource.data.filterNot { user ->
//                        user.id in excludedUserIds.value
//                    })
//                } else {
//                    resource
//                }
//            }.collect {
//                _usersList.value = _usersList.value.map { it.copy(isSelect = false) }
//            }
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

//    private fun userId(id: MutableList<Int>) {
//        viewModelScope.launch {
//            _excludedUserIds.value = id
//        }
//        if (id.isEmpty()) {
//            _excludedUserIds.value = emptyList()
//        }
//        getUsersList()
//    }



}