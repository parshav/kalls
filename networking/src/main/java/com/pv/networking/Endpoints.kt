package com.pv.networking

import arrow.core.some
import com.pv.networking.models.history.HistoryModel

object Endpoints : Kalls("https://api.spacexdata.com/v3") {

    private val launches ="/launches"<Launch5> {

        "latest" <Launch5> {

        } referAs "latest launch"

        "next" <Launch5> {

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
            makeKall("dynamic spacex history",
                    "n" pairWith amount.toString(),
                    callback = callback)
}

object test {

    fun test() {
        Endpoints.callHistoryForAmount(1) {

        }
    }
}