package com.example.userlist.presentation.screen.current_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.common.Resource
import com.example.userlist.domain.usecase.GetCurrentUserUseCase
import com.example.userlist.presentation.model.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentUserViewModel @Inject constructor(
    private val currentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
    private val _currentUser = MutableStateFlow<Resource<Users>?>(Resource.Loading())
    val getUser: StateFlow<Resource<Users>?> = _currentUser.asStateFlow()

    fun getCurrentUser(id: Int) {
        viewModelScope.launch {
            currentUserUseCase.invoke(id = id).collect {
                _currentUser.value = it
            }
        }
    }
}