package com.example.userlist.presentation.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.common.Resource
import com.example.userlist.domain.model.Users
import com.example.userlist.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _usersList = MutableStateFlow<Resource<List<Users>>?>(Resource.Loading(false))
    val usersList: StateFlow<Resource<List<Users>>?> = _usersList.asStateFlow()

    init {
        getUsersList()
    }

    private fun getUsersList() {
        viewModelScope.launch {
            getUsersUseCase.invoke().collect {
                _usersList.value = it
            }
            val users = getUsersUseCase.invoke()
            d("viewModelviewModel", "viewModel: $usersList")
        }
    }

}