package com.pv.networking

import arrow.core.Some
import arrow.core.some
import com.github.kittinunf.fuel.core.FuelManager

typealias ApiKey = String

class Kalls(baseUrl: String) {

    private val apis = mutableMapOf<Any, String>()

    init {
        FuelManager.instance.basePath = baseUrl
    }

    operator fun<T> String.invoke(block: Api<T>.() -> (Unit)): Api<T> {
        val a = Api<T>()
        a.block()
        apis[a] = this
        return a
    }

    enum class Type {
        GET, POST
    }

    fun<T> call(block: (String) -> Unit) {
        val api = Api<T>()
        apis[api]?.let {
            block.invoke(it)
        } ?: run {
            block.invoke("Not found breh")
        }
    }
}

class Api<T>() {

    /*operator fun String.invoke( block: Api.() -> (Unit)) {
    }*/
    infix fun String.goes(id: String) {

    }

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