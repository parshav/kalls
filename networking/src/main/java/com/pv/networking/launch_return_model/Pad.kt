package com.pv.networking.launch_return_model

data class Pad(
        val id: Int,
        val name: String,
        val infoURL: String,
        val wikiURL: String,
        val mapURL: String,
        val latitude: Double,
        val longitude: Double,
        val agencies: List<Agency>
)