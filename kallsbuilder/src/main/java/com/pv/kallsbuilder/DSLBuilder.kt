package com.pv.kallsbuilder

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet

open class Kalls(val baseUrl: String) {

    val pathToApi = mutableMapOf<String, Api>()
    val typeToPath = mutableMapOf<Any, String>()
    val referToApi = mutableMapOf<String, Api>()

    inline operator fun <reified T> String.invoke(block: Api.() -> (Unit)): Api {
        val a = Api(this)
        a.block()
        typeToPath[T::class.java] = this
        pathToApi[this] = a
        return a
    }

    infix fun Api.referAs(string: String) {
        referToApi[string] = this
    }

    inline fun <reified T> makeKall(vararg params: Pair<String, String>, crossinline callback: Kallback<T>) {

        var paramList: MutableList<Pair<String, String>>

        typeToPath[T::class.java]?.let { path ->
            pathToApi[path]?.let { api ->
                paramList = params.filter { api.parameters.containsKey(it.first) }.toMutableList()
                callback.invoke(path.replacePathwithParam(paramList).left())
            } ?: run {
                callback.invoke("Type mismatch path".left())
            }
        } ?: run {
            callback.invoke("Type mismatch or not found".left())
        }
    }

    inline fun <reified T> makeKallGroup(ref: String, inner: String, crossinline callback: Kallback<T>) {
        referToApi[ref]?.let { api ->
            api.referToInner[inner]?.let { inner ->
                //                kall(api.ext + inner.ext, kallback = callback)
                callback.invoke((api.ext + inner.ext).left())
            } ?: run {
                callback.invoke("Didn't find inner".left())
            }
        } ?: run {
            callback.invoke("Didn't find API2".left())
        }
    }

    inline fun <reified T> kall(path: String, crossinline kallback: Kallback<T>) {
        (baseUrl + path).httpGet().responseString { req, res, r ->
            r.fold(
                    { respString ->
                        Klaxon().parse<T>(respString)?.let {
                            kallback.invoke(it.right())
                        } ?: run {
                            kallback.invoke("Parse Error".left())
                        }
                    },
                    {
                        kallback.invoke("Fuel Error ${it.exception}".left())
                    }
            )
        }
    }

    fun String.replacePathwithParam(param: List<Pair<String, String>>): String {
        var mut = this
        param.forEach {
            mut = mut.replace("{${it.first}}", it.second)
        }
        return mut
    }

    operator fun String.invoke() {

    }
}

class Api(val ext: String) {

    lateinit var handleError: Error
    val parameters = mutableMapOf<String, String>()
    val referToInner = mutableMapOf<String, InnerApi>()

    inline operator fun <reified T> String.invoke(block: InnerApi.() -> (Unit)): InnerApi {
        val inner = InnerApi(this)
        inner.block()
        return inner
    }

    infix fun InnerApi.referAs(string: String) {
        referToInner[string] = this
    }
}

class InnerApi(val ext: String) {

    val params = mutableMapOf<String, String>()
}

typealias Kallback<T> = (Either<String, T>) -> Unit

object None

infix fun String.pairWith(with: String) = Pair(this, with)

fun kall(baseUrl: String, block: Kalls.() -> (Unit)): Kalls {
    val kall = Kalls(baseUrl)
    kall.block()
    return kall
}