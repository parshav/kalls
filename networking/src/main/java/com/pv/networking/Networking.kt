package com.pv.networking

import android.util.Log
import arrow.core.Either
import arrow.core.None
import com.pv.networking.models.history.HistoryModel
import com.pv.networking.models.launches.LaunchModel
import com.pv.networking.models.roadster.RoadsterModel

object Networking {

    val api = kall("https://api.spacexdata.com/v3") {

        "/launches"<None> {

            "/latest"<LatestLaunchModel> {

            } referAs "latest"

            "/next"<NextLaunchModel> {

            } referAs "next"

        } referAs "launches"

        "/history/{n}"<HistoryModel> {

            parameters["n"] = "1" // default

        }

        "/roadster"<RoadsterModel> {

        }
    }

    fun test() {

        historyFor(1) {
            it.fold(
                    {
                        Log.d("pv", "Error in History $it")
                    },
                    {
                        Log.d("pv", "HustoryModel Returned boi")
                    }
            )
        }

        latestLaunch {
            it.fold(
                    {
                        Log.d("pv", "Error in latest launch $it")
                    },
                    {
                        Log.d("pv", "LatestLaunchModel Returned boi")
                    }
            )
        }
    }

    fun latestLaunch(callback: Kallback<LatestLaunchModel>) =
            api.makeKallGroup("launches", "latest", callback)

    fun historyFor(number: Int, callback: Kallback<HistoryModel>) =
            api.makeKall(
                    params = *arrayOf("n" pairWith number.toString()),
                    callback = callback
            )

    fun roadster(callback: Kallback<RoadsterModel>) =
            api.makeKall(callback = callback)
}

typealias Kallback<T> = (Either<String, T>) -> Unit


typealias NextLaunchModel = LaunchModel
typealias LatestLaunchModel = LaunchModel


object LaunchError : Error()