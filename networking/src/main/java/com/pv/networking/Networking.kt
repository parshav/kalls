package com.pv.networking

import android.util.Log
import com.pv.networking.Kalls.Companion.self
import com.pv.networking.models.history.HistoryModel


typealias launchFunCall = (LaunchReturn) -> Unit

object Networking {

    val api = kall("https://api.spacexdata.com/v3") {

        "/launches"<Launch5> {

            "latest" <Launch5> {

            } referAs "latest launch"

            "next" <Launch5> {

            } referAs "next launch"

        } referAs "launches"

        "/history/1"<HistoryModel> {

        } referAs "single spacex history"

        "/history/{n}"<HistoryModel> {

            handleError = LaunchError
            parameters["n"] = "1" // default

        } referAs "dynamic spacex history"
    }

    fun test() {
        api kall "next five"

        api.makeKall<HistoryModel>(
                "dynamic spacex history",
                "n" pairWith "2") {

            Log.d("pv", "makeKall : $it")
        }
    }

//    fun launchCall(callBack: (String) -> Unit) = api.call<LaunchReturn>(callBack)

    //    fun launchCal(callBack: (Pair<Boolean, LaunchReturn>) -> Unit) = api.call2(callBack)
    fun authCall() = api.sss["LSD"]
}

data class LaunchReturn(val string: String)
data class MissionReturn(val string: String)

typealias Launch5 = LaunchReturn

data class LaunchRequest(val string: String) : KallRequest()

object LaunchError : Error()