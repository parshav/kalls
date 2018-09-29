package com.pv.networking

import android.util.Log
import com.pv.networking.Kalls.Companion.self
import com.pv.networking.models.history.HistoryModel


typealias launchFunCall = (LaunchReturn) -> Unit

object Networking {

    val api = kall ("https://api.spacexdata.com/v3/") {

        "/launch/next/n" <Launch5> {

            params["n"] = "10"

            "" <Launch5> {

                params["n"] = "5"

            } referAs "five launches"

        } referAs self

        "/history/1" <MissionReturn> {

//            handleError = LaunchError

        } referAs "main missions"

        val next5 = "launch/next/5" <LaunchReturn> {

        } referAs "next five"
    }

    fun test() {

        /*launchCall {
            Log.d("pv", "The string is $it")
        }*/

        api kall "next five"

        api.makeKall<HistoryModel>("next five") {
            println("makeKall : $it")
        }
      /*  launchCal {

        }
        */
        "hello"
    }

    fun launchCall(callBack: (String) -> Unit) = api.call<LaunchReturn> (callBack)

//    fun launchCal(callBack: (Pair<Boolean, LaunchReturn>) -> Unit) = api.call2(callBack)
    fun authCall() = api.sss["LSD"]

}

data class LaunchReturn(val string: String)
data class MissionReturn(val string: String)

typealias Launch5 = LaunchReturn
data class LaunchRequest(val string: String): KallRequest()

object LaunchError : Error()