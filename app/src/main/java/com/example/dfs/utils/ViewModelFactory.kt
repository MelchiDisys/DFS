package com.example.dfs.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dfs.Api.APIInterface
import com.example.dfs.LoginScreen.viewModel.LoginViewModel
import com.example.dfs.Repository.Repository

class ViewModelFactory(private val apiHelper: APIInterface, var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
             LoginViewModel(Repository(apiHelper),application = application) as T
        } else{
            throw IllegalArgumentException("Unknown class name")
        }
    }
}