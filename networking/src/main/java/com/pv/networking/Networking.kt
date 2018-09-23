package com.pv.networking

import android.util.Log


typealias launchFunCall = (LaunchReturn) -> Unit

object Networking {

    val api = kall ("https://launchlibrary.net/1.4/") {

        "launch/next/n" <LaunchReturn> {

        }

        "missions" <MissionReturn> {

            handleError = LaunchError

        } referAs "main missions"

        val next5 = "launch/next/5" <LaunchReturn> {

        }

        next5 referAs "LSD"
    }

    fun test() {

        launchCall {
            Log.d("pv", "The string is $it")
        }

        api kall "LSD"

    }

    fun launchCall(callBack: (String) -> Unit) = api.call<LaunchReturn> (callBack)

}

data class LaunchReturn(val string: String)
data class MissionReturn(val string: String)

data class LaunchRequest(val string: String): KallRequest()

object LaunchError : Error()