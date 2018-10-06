package com.pv.networking

import android.util.Log
import arrow.core.Either
import arrow.core.left
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

typealias ApiKey = String

class Kalls(baseUrl: String) {

    companion object {
        const val self = "kalls kalls"
    }

    val apis = mutableMapOf<Api2, String>()
    val sips = mutableMapOf<String, Api2>()

    val sss = mutableMapOf<String, String>()
    val params = mutableMapOf<String, String>()


    init {
        FuelManager.instance.basePath = baseUrl
    }

    /*operator fun <T> String.invoke(block: Api<T>.() -> (Unit)): Api<T> {
        val a = Api<T>(this)
        a.block()
        apis[a] = this
        return a
    }*/

    inline operator fun <reified T> String.invoke(block: Api2.() -> (Unit)): Api2 {
        val a = Api2(this)
        a.block()
        apis[a] = this
        sips[this] = a
        return a
    }

    /* inline operator fun<reified T> String.invoke(block: Api2.() -> (Unit)): Api2 {
         val a = Api2(this)
         val k = Klaxon().parse<T>(" ")
         sss[this] = a
         return a
     }
 */

    infix fun Api2.referAs(string: String) {
        sss[string] = this.ext
    }

    /*fun <T> call(block: (String) -> Unit) {
        val api = Api<T>("")
        apis[api]?.let {
            block.invoke(it)
        } ?: run {
            block.invoke("Not found breh")
        }
    }*/

/*    inline fun <reified T> call2(callback: (Pair<Boolean, T>) -> (Unit)) {
        var ext = ""
        ext.httpGet().responseString { _, _, r ->
            r.fold({
                val k = Klaxon().parse<T>(it)
                k?.let {
                }
            }, {

            })
        }
    }*/

    inline fun <reified T> makeKall(ref: String, vararg params: Pair<String, String>, crossinline callback: (Either<String, T>) -> Unit) {

        var paramList = mutableListOf<Pair<String, String>>()
        sss[ref]?.let { path ->
            sips[path]?.let { api2 ->
                paramList = params.filter { api2.parameters.containsKey(it.first) }.toMutableList()
            } ?: run {
                Log.d("pv", "no params found")
            }

            Log.d("pv", "request : ${path.replacePathwithParam(paramList)}")
            /*it.httpGet().responseString { req, res, r ->
                Klaxon().parse<T>(r.get())?.let {
                    callback.invoke(it.right())
                } ?: run {
                    callback.invoke("Parse Error".left())
                }
            }*/
        } ?: run {
            callback.invoke("Not found".left())
        }
    }

    fun String.replacePathwithParam(param: List<Pair<String,String>>): String {
        var mut = this
        param.forEach {
            mut = mut.replace("{${it.first}}", it.second)
        }
        return mut
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
}

class Api2(val ext: String) {

    lateinit var handleError: Error
    val parameters = mutableMapOf<String, String>()

}

open class KallRequest

infix fun String.pairWith(with: String) = Pair(this, with)

fun kall(baseUrl: String, block: Kalls.() -> (Unit)): Kalls {
    val kall = Kalls(baseUrl)
    kall.block()
    return kall
}

fun Kalls.generateRequest(ok: Boolean) {

}