package com.pv.networking.models.launches

data class LaunchModel(
        val flight_number: Int? = 0,
        val mission_name: String? = "",
        val mission_id: List<String>? = emptyList(),
        val launch_year: String? = "",
        val launch_date_unix: Int? = 0,
        val launch_date_utc: String? = "",
        val launch_date_local: String? = "",
        val is_tentative: Boolean? = false,
        val tentative_max_precision: String? = "",
        val rocket: Rocket,
        val ships: List<String>? = emptyList(),
        val telemetry: Telemetry,
        val launch_site: LaunchSite,
        val launch_success: Boolean? = false,
        val links: Links,
        val details: String? = "",
        val upcoming: Boolean? = false,
        val static_fire_date_utc: String? = "",
        val static_fire_date_unix: Int? = 0
)