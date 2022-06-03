package com.example.accuratetask.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.example.accuratetask.App
import com.example.accuratetask.api.RestClient
import com.example.accuratetask.api.Service
import com.example.accuratetask.model.entity.User
import com.example.accuratetask.util.networkBoundResource
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository (private val service: Service) {

    private val db = App.context.database
    private val api = RestClient.getService()

    fun getUserList(sort: String?, filter: String?, filterValue: String?) = networkBoundResource(
        query = {
            db.userDao().getUserList(sort, filter, filterValue)
        },
        fetch = {
            delay(2000)
            service.getUsers()
        },
        saveFetchResult = { users ->
            db.withTransaction {
                db.userDao().insertUser(users)
            }
        }
    )

    fun getCityList() = networkBoundResource(
        query = {
            db.cityDao().getCityList()
        },
        fetch = {
            delay(2000)
            service.getCities()
        },
        saveFetchResult = { cities ->
            db.withTransaction {
                db.cityDao().insertCity(cities)
            }
        }
    )


    fun addUser(user: User) : LiveData<User?> {
        val livedata: MutableLiveData<User?> = MutableLiveData()

        val call = api.addUser(user)
        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) livedata.value = response.body()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("myapp", t.message.toString())
            }
        })

        return livedata
    }


}