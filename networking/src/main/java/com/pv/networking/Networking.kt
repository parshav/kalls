package com.pv.networking

import com.pv.kallsbuilder.Kallback
import com.pv.kallsbuilder.None
import com.pv.kallsbuilder.kall
import com.pv.kallsbuilder.pairWith
import com.pv.networking.models.history.HistoryModel
import com.pv.networking.models.launches.LaunchModel
import com.pv.networking.models.roadster.RoadsterModel

object Networking {

    private val api = kall("https://api.spacexdata.com/v3") {

        "/launches"<None> {

            "/latest"<LatestLaunchModel> {

            } referAs "latest"

            "/next"<NextLaunchModel> {

            } referAs "next"

        } referAs "launches"

        "/history/{n}"<HistoryModel> {

            parameters["n"] = "1" // default

        } referAs "dynamic history"

        "/roadster"<RoadsterModel> {

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

    fun dynamicHistory(number: Int, callback: Kallback<HistoryModel>) =
            api.makeKall(
                    ref = "dynamic history",
                    params = *arrayOf("n" pairWith number.toString()),
                    callback = callback
            )
}

typealias NextLaunchModel = LaunchModel
typealias LatestLaunchModel = LaunchModel


object LaunchError : Error()