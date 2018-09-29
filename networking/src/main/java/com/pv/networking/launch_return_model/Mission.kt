package com.pv.networking.launch_return_model

data class Mission(
        val id: Int,
        val name: String,
        val description: String,
        val type: Int,
        val wikiURL: String,
        val typeName: String,
        val agencies: List<AgencyX>?,
        val payloads: List<Any>
)