package com.pv.networking

import com.pv.networking.models.history.HistoryModel
import com.pv.networking.models.launches.LaunchModel

object Endpoints : Kalls("https://api.spacexdata.com/v3") {

    private val launches = "/launches"<LaunchModel> {

        "latest"<LaunchModel> {

        } referAs "latest launch"

        "next"<LaunchModel> {

        } referAs "next launch"

    } referAs "launches"

    private val singleHistory = "/history/1"<HistoryModel> {

    } referAs "single spacex history"

    private val dynamicHistory = "/history/{n}"<HistoryModel> {

        handleError = LaunchError
        parameters["n"] = "1" // default

    } referAs "dynamic spacex history"

    fun A() {
    }

    fun callHistoryForAmount(amount: Int,
                             callback: Kallback<HistoryModel>) =
            makeKall(
                    "n" pairWith amount.toString(),
                    callback = callback)
}

object test {

    fun test() {
        Endpoints.callHistoryForAmount(1) {

        }
    }
}