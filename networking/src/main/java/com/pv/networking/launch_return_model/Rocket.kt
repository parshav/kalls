package com.pv.networking.launch_return_model

data class Rocket(
        val id: Int,
        val name: String,
        val configuration: String,
        val familyname: String,
        val agencies: List<AgencyX>,
        val wikiURL: String,
        val infoURLs: List<Any>,
        val imageURL: String,
        val imageSizes: List<Int>
)