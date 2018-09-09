package com.pv.networking

object Networking {

    val api = kall ("https://launchlibrary.net/1.4") {

        "launch" <LaunchReturn> {

        }

        "missions" <MissionReturn> {
            " " goes " "

        }
    }

    fun test() {

    }

    fun launchCall(callBack: (String) -> Unit) = api.call<LaunchReturn> (callBack)

}

data class LaunchReturn(val string: String)
data class MissionReturn(val string: String)