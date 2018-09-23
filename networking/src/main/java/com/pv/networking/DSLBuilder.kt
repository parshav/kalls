package com.pv.networking

import arrow.core.some
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import java.lang.reflect.Type

typealias ApiKey = String

class Kalls(baseUrl: String) {

    val apis = mutableMapOf<Any, String>()
    val sss = mutableMapOf<String, Any>()

    init {
        FuelManager.instance.basePath = baseUrl
    }

    operator fun<T> String.invoke(block: Api<T>.() -> (Unit)): Api<T> {
        val a = Api<T>(this)
        a.block()
        apis[a] = this
        return a
    }

    fun<T : KallRequest> kallRequest(block: Api<T>.() -> Unit): Api<T> {
        val a = Api<T>("")
        a.block()
        apis[a] = " "
        return a
    }

    infix fun<T> Api<T>.referAs(string: String) {
        sss[string] = this
    }

    infix fun String.callback(string: String) {

    }


    fun<T> call(block: (String) -> Unit) {
        val api = Api<T>("")
        apis[api]?.let {
            block.invoke(it)
        } ?: run {
            block.invoke("Not found breh")
        }
    }

    fun call2(callback: (Pair<Boolean, T>) -> (Unit)) {

        ext.httpGet().responseString { _, _, r ->
            r.fold({
                //                val type: Type = callback::class.java
                val k = Klaxon().parse<Type>("")
                k?.let {
                }
            }, {

            })
        }
    }

    fun makeKall() {

    }

    operator fun String.invoke() {

    }
}


infix fun Kalls.kall(something: String) {
    sss[something]
}



class Api<T>(private val ext: String) {

    lateinit var handleError: Error

    fun call(callback: (Pair<Boolean, T>) -> (Unit)) {
        ext.httpGet().responseString { _, _, r ->
            r.fold({
//                val type: Type = callback::class.java
                val k = Klaxon().parse<Type>("")
                k?.let {
                }
            }, {

            })
        }
    }
}

open class KallRequest

fun kall(baseUrl: String, block: Kalls.() -> (Unit)): Kalls {
    val kall = Kalls(baseUrl)
    kall.block()
    return kall
}

fun Kalls.generateRequest(ok: Boolean) {

}