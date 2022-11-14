package com.teampome.pome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampome.pome.model.AddFriendsTestData
import com.teampome.pome.repository.AddFriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFriendsViewModel @Inject constructor(
    private val repository: AddFriendsRepository
) : ViewModel() {

    private val _testFriendsList = MutableLiveData<List<AddFriendsTestData>>()
    val testFriendsList: LiveData<List<AddFriendsTestData>> = _testFriendsList

    init {
        getTestData()
    }

    private fun getTestData() {
        viewModelScope.launch {
            _testFriendsList.value = repository.getFriendsTestData()
        }
    }
}