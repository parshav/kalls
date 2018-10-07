package com.pv.networking.models.launches

data class Payload(
        val payload_id: String? = "",
        val norad_id: List<Int>? = emptyList(),
        val reused: Boolean? = false,
        val customers: List<String> = emptyList(),
        val nationality: String? = "",
        val manufacturer: String? = "",
        val payload_type: String? = "",
        val payload_mass_kg: Int? = 0,
        val payload_mass_lbs: Double? = 0.0,
        val orbit: String? = "",
        val orbit_params: OrbitParams
)