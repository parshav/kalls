package com.pv.networking.launch_return_model

data class LaunchReturnDataClass(
        val launches: List<Launche>,
        val total: Int,
        val offset: Int,
        val count: Int
)