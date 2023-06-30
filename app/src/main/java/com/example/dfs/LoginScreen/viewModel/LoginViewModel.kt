package com.example.dfs.LoginScreen.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.dfs.Repository.Repository
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.example.dfs.R
import com.example.dfs.utils.Resource
import com.example.dfs.utils.Utility
import com.google.gson.JsonObject

class LoginViewModel(val repository: Repository, application: Application): AndroidViewModel(application) {
    var error = "Error Occurred!"

    fun postLoginResponse(jsonObject: JsonObject) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.postLogin(jsonObject)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getUserServiceRequest(userId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.getUserServiceRequest(userId)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getServiceType() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.getServiceType()))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getServices(serviceId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.getService(serviceId)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getServicesProvider(serviceId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.getServicesProvider(serviceId)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun postService(userId: Int, jsonObject: JsonObject) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.postService(userId, jsonObject)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getItemsList(userId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.getItemsList(userId)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun postAddToCart(userId: Int, jsonObject: JsonObject) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.postAddToCart(userId, jsonObject)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getItemsCart(userId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.getCartItems(userId)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun deleteItem(userId: Int, itemId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (Utility.isConnected(getApplication())) {
                emit(Resource.success(data = repository.deleteItem(userId, itemId)))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = getApplication<Application>().getString(R.string.network_slow_or_disconnected)
                    )
                )
            }
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


}