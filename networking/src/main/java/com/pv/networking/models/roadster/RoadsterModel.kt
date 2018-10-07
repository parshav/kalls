package com.pv.networking.models.roadster

data class RoadsterModel(
        val name: String,
        val launch_date_utc: String,
        val launch_date_unix: Int,
        val launch_mass_kg: Int,
        val launch_mass_lbs: Int,
        val norad_id: Int,
        val epoch_jd: Double,
        val orbit_type: String,
        val apoapsis_au: Double,
        val periapsis_au: Double,
        val semi_major_axis_au: Double,
        val eccentricity: Double,
        val inclination: Double,
        val longitude: Double,
        val periapsis_arg: Double,
        val period_days: Double,
        val speed_kph: Double,
        val speed_mph: Double,
        val earth_distance_km: Double,
        val earth_distance_mi: Double,
        val mars_distance_km: Double,
        val mars_distance_mi: Double,
        val wikipedia: String,
        val details: String
)