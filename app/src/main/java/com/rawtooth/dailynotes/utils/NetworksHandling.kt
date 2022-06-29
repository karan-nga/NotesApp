package com.rawtooth.dailynotes.utils

sealed class NetworksHandling<T>(val data:T? =null,val message:String?=null )  {
    class Success<T>(data: T?):NetworksHandling<T>(data)
    class Error<T>(message: String?,data: T?=null):NetworksHandling<T>(data,message)
    class Loading<T>:NetworksHandling<T>()
}