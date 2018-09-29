package com.pv.networking.launch_return_model

data class Location(
        val pads: List<Pad>,
        val id: Int,
        val name: String,
        val infoURL: String,
        val wikiURL: String,
        val countryCode: String
)