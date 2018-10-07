package com.pv.networking.models.launches

data class OrbitParams(
        val reference_system: String? = "",
        val regime: String? = "",
        val longitude: Int? = 0,
        val semi_major_axis_km: Double? = 0.0,
        val eccentricity: Double? = 0.0,
        val periapsis_km: Double? = 0.0,
        val apoapsis_km: Double? = 0.0,
        val inclination_deg: Double? = 0.0,
        val period_min: Double? = 0.0,
        val lifespan_years: Int? = 0,
        val epoch: String? = "",
        val mean_motion: Double? = 0.0,
        val raan: Double? = 0.0,
        val arg_of_pericenter: Double? = 0.0,
        val mean_anomaly: Double? = 0.0
)