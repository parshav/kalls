package com.pv.networking.models.launches

data class SecondStage(
        val block: Int? = 0,
        val payloads: List<Payload>
)