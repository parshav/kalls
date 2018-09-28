package com.pv.networking

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import kotlin.reflect.KClass

typealias ApiKey = String

class Kalls(baseUrl: String) {

    companion object {
        const val self = "kalls kalls"
    }

    val apis = mutableMapOf<Any, String>()
    val sss = mutableMapOf<String, Any>()
    val params = mutableMapOf<String, String>()
    val ss = mutableSetOf<KClass<Any>>()

    init {
        FuelManager.instance.basePath = baseUrl
    }

    operator fun <T> String.invoke(block: Api<T>.() -> (Unit)): Api<T> {
        val a = Api<T>(this)
        a.block()
        apis[a] = this
        return a
    }

    /* inline operator fun<reified T> String.invoke(block: Api2.() -> (Unit)): Api2 {
         val a = Api2(this)
         val k = Klaxon().parse<T>(" ")
         sss[this] = a
         return a
     }
 */
    fun <T : KallRequest> kallRequest(block: Api<T>.() -> Unit): Api<T> {
        val a = Api<T>("")
        a.block()
        apis[a] = " "
        return a
    }

    infix fun <T> Api<T>.referAs(string: String) {
        sss[string] = this
    }

    fun String.callback(string: String) {

    }


    fun <T> call(block: (String) -> Unit) {
        val api = Api<T>("")
        apis[api]?.let {
            block.invoke(it)
        } ?: run {
            block.invoke("Not found breh")
        }
    }

/*    inline fun <reified T> call2(callback: (Pair<Boolean, T>) -> (Unit)) {
        ext.httpGet().responseString { _, _, r ->
            r.fold({
                val k = Klaxon().parse<T>(it)
                k?.let {
                }
            }, {

            })
        }
    }*/

    fun makeKall() {

    }

    operator fun String.invoke() {

    }
}


infix fun Kalls.kall(something: String) {
    sss[something]
}


class Api<T>(
        val ext: String
) {


    inline fun <reified G> call(callback: (Pair<Boolean, G>) -> (Unit)) {

        ext.httpGet().responseString { _, _, r ->
            r.fold({
                val k = Klaxon().parse<G>(it)
                k?.let {
                }
            }, {

            })
        }
    }
}

class Api2(val ext: String) {

    lateinit var handleError: Error

    inline fun <reified T> call(callback: (Pair<Boolean, T>) -> (Unit)) {

        ext.httpGet().responseString { _, _, r ->
            r.fold({
                val k = Klaxon().parse<T>(it)
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