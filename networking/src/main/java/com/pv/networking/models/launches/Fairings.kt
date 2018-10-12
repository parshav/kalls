package com.pv.networking.models.launches

data class Fairings(
        val reused: Boolean? = false,
        val recovery_attempt: Boolean? = false,
        val recovered: Boolean? = false,
        val ship: String? = ""
)