package com.pv.networking.models.launches

data class Core(
        val core_serial: String? = "",
        val flight: Int? = 0,
        val block: Int? = 0,
        val reused: Boolean? = false,
        val land_success: Boolean? = false,
        val landing_intent: Boolean? = false,
        val landing_type: String? = "",
        val landing_vehicle: String? = ""
)