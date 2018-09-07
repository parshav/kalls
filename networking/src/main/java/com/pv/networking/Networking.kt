package com.pv.networking

object Networking {

    val api = kall ("https://launchlibrary.net/1.4") {

        "launch" <LaunchReturn> {

        }

        "missions" <MissionReturn> {

        }
    }

    fun test() {
    }
}


data class LaunchReturn(val string: String)
data class MissionReturn(val string: String)