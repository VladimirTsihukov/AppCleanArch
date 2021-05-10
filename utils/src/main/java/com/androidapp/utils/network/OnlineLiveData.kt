package com.androidapp.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

class OnlineLiveData (context: Context) : LiveData<Boolean>() {

    //массив из доступных сетей
    private val availableNetworks = mutableSetOf<Network>()
    //получаем connectivityManager
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //Создаем запрос
    private val request: NetworkRequest = NetworkRequest.Builder().build()

    //создаем колбек который уведомляет нас о появлении или исчезновении связи с сетью
    private val callback = object : ConnectivityManager.NetworkCallback() {

        //если соединение потеряно, убирает его из массива и уведомляет полписчиков о изм связи
        override fun onLost(network: Network) {
            availableNetworks.remove(network)
            update(availableNetworks.isNotEmpty())
        }

        //соединение восстановлено, добовляем в массив уведомляем подписчиков
        override fun onAvailable(network: Network) {
            availableNetworks.add(network)
            update(availableNetworks.isNotEmpty())
        }
    }

    //Регистрирум колбэк если компонент, подписанный на LiveData активен
    override fun onActive() {
        connectivityManager.registerNetworkCallback(request, callback)

    }

    //Убирвем колбек если компонент не активен
    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    fun isNetworkAvailable() = connectivityManager.allNetworks.isNotEmpty()

    private fun update(online: Boolean) {
        if (online != value) {
            postValue(online)
        }
    }
}