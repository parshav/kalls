package com.pv.networking

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

typealias ApiKey = String

class Kalls(baseUrl: String) {

//    private val apis = mutableMapOf<Any, Api<T>>()
    init {
        FuelManager.instance.basePath = baseUrl
    }


    operator fun<T> String.invoke(block: Api<T>.() -> (Unit)): Api<T> {
        val a = Api<T>(this)
        a.block()
        return a
    }

    infix fun String.goes(id: String) {

    }

    enum class Type {
        GET, POST
    }
}

class Api<T>(private val ext: String) {

    /*operator fun String.invoke( block: Api.() -> (Unit)) {
    }*/

    fun call() {
    }
}

fun kall(baseUrl: String, block: Kalls.() -> (Unit)): Kalls {
    val kall = Kalls(baseUrl)
    kall.block()
    return kall
}

fun Kalls.generateRequest(ok: Boolean) {

}