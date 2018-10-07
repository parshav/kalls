package com.pv.networking

import android.util.Log
import arrow.core.Either
import arrow.core.None
import com.pv.networking.models.history.HistoryModel
import com.pv.networking.models.launches.LaunchModel


typealias launchFunCall = (LaunchReturn) -> Unit

object Networking {

    val api = kall("https://api.spacexdata.com/v3") {

        "/launches"<None> {

            "/latest"<LatestLaunchModel> {

            } referAs "latest"

            "/next"<NextLaunchModel> {

            } referAs "next"

        } referAs "launches"

        "/history/1"<SingleHistoryModel> {

        } referAs "single spacex history"

        "/history/{n}"<DynamicHistoryModel> {

            handleError = LaunchError
            parameters["n"] = "1" // default

        } referAs "dynamic spacex history"
    }

    fun test() {
        api kall "next five"

        /*api.makeKall<SingleHistoryModel>(
                "dynamic spacex history",
                "n" pairWith "2") {

            Log.d("pv", "makeKall : $it")
        }*/

        latestLaunch {
            it.fold({
                Log.d("pv", "error in latest launch $it")
            }, {
                Log.d("pv", "LatestLaunchModelReturned boiz")
            })
        }
    }

    fun latestLaunch(callback: Kallback<LatestLaunchModel>)
            = api.makeKallGroup("launches", "latest", callback)
//    fun launchCall(callBack: (String) -> Unit) = api.call<LaunchReturn>(callBack)

    //    fun launchCal(callBack: (Pair<Boolean, LaunchReturn>) -> Unit) = api.call2(callBack)
    fun authCall() = api.sss["LSD"]
}

typealias Kallback<T> = (Either<String, T>) -> Unit


typealias NextLaunchModel = LaunchModel
typealias LatestLaunchModel = LaunchModel

typealias SingleHistoryModel = HistoryModel
typealias DynamicHistoryModel = HistoryModel

data class LaunchReturn(val string: String)

typealias Launch5 = LaunchReturn

data class LaunchRequest(val string: String) : KallRequest()

object LaunchError : Error()