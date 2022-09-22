package com.rafi.suitmediatest.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafi.suitmediatest.api.ApiConfig
import com.rafi.suitmediatest.api.DataItem
import com.rafi.suitmediatest.api.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<DataItem>>()
    val listUser: LiveData<List<DataItem>> = _listUser

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val parameters = HashMap<String, String>()

    fun getUser(parameters: Map<String, String>) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(parameters as HashMap<String, String>)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.data
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    init {
        getUser(parameters)
    }
}