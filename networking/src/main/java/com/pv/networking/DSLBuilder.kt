package com.pv.networking

import arrow.core.left
import arrow.core.right
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

open class Kalls(baseUrl: String) {

    val apis = mutableMapOf<Api2, String>()
    val sips = mutableMapOf<String, Api2>()
    val typeToPath = mutableMapOf<Any, String>()

    val sss = mutableMapOf<String, String>()
    val referToApi2 = mutableMapOf<String, Api2>()

    init {
        FuelManager.instance.basePath = baseUrl
    }

    inline operator fun <reified T> String.invoke(block: Api2.() -> (Unit)): Api2 {
        val a = Api2(this)
        a.block()
        apis[a] = this
        typeToPath[T::class.java] = this
        sips[this] = a
        return a
    }

    infix fun Api2.referAs(string: String) {
        sss[string] = this.ext
        referToApi2[string] = this
    }
    // if string is pased, look for Api2 object , then generics
    // else just look for matching

    inline fun <reified T> makeKall(ref: String = "", vararg params: Pair<String, String>, crossinline callback: Kallback<T>) {

        var paramList: MutableList<Pair<String, String>>

        typeToPath[T::class.java]?.let { path ->
            sips[path]?.let { api2 ->
                paramList = params.filter { api2.parameters.containsKey(it.first) }.toMutableList()
                callback.invoke(path.replacePathwithParam(paramList).left())
            } ?: run {
                callback.invoke("Type mismatch path".left())
            }
        } ?: run {
            callback.invoke("Type mismatch or not found".left())
        }

        /*sss[if (!ref.isBlank()) ref else c]?.let { path ->
            sips[path]?.let { api2 ->
                paramList = params.filter { api2.parameters.containsKey(it.first) }.toMutableList()
            } ?: run {
                Log.d("pv", "no params found")
            }

//            kall(path.replacePathwithParam(paramList), callback)
            callback.invoke(path.replacePathwithParam(paramList).left())
        } ?: run {
            if (c == null)
                callback.invoke("Type Mismatch".left())
            else
                callback.invoke("Reference not found".left())
        }*/
    }

    inline fun <reified T> makeKallGroup(ref: String, inner: String, crossinline callback: Kallback<T>) {
        referToApi2[ref]?.let { api2 ->
            api2.referToInner[inner]?.let { inner ->
                //                kall(api2.ext + inner.ext, kallback = callback)
                callback.invoke((api2.ext + inner.ext).left())
            } ?: run {
                callback.invoke("Didn't find inner".left())
            }
        } ?: run {
            callback.invoke("Didn't find API2".left())
        }
    }

    inline fun <reified T> kall(path: String, crossinline kallback: Kallback<T>) {
        path.httpGet().responseString { req, res, r ->
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
    val innerApis = mutableMapOf<String, InnerApi>()
    val referToInner = mutableMapOf<String, InnerApi>()
    val Attempt = mutableMapOf<Any, String>()

    inline operator fun <reified T> String.invoke(block: InnerApi.() -> (Unit)): InnerApi {
        val inner = InnerApi(this)
        inner.block()
        innerApis[this] = inner
        Attempt[T::class.java] = this
        return inner
    }

    infix fun InnerApi.referAs(string: String) {
        referToInner[string] = this
    }
}

class InnerApi(val ext: String) {

    val params = mutableMapOf<String, String>()


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