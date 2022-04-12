package com.feiyatsu.reviewroomdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feiyatsu.reviewroomdb.data.User
import com.feiyatsu.reviewroomdb.data.UserDao
import com.feiyatsu.reviewroomdb.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val readAllData: LiveData<List<User>> = userRepository.readAllData

    fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.addUser(user)
        }
    }
}