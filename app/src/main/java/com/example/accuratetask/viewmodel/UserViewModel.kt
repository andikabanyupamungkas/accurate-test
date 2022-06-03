package com.example.accuratetask.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.accuratetask.model.entity.User
import com.example.accuratetask.model.repository.Repository
import com.example.accuratetask.util.Resource
import java.lang.IllegalArgumentException

class UserViewModel(private val repository: Repository) : ViewModel() {

    fun getUserList(sort: String?, searchText: String?) : LiveData<Resource<List<User>>> {
        val f = searchText?.let { s ->
            if (s.isNotEmpty()) "filter" else null
        }
        Log.d("myapp", f.toString())

        return repository.getUserList(sort, f, searchText).asLiveData()
    }

    val cityList = repository.getCityList().asLiveData()

    fun addUser(user: User) : LiveData<User?> {
        return repository.addUser(user)
    }
}

class UserViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}