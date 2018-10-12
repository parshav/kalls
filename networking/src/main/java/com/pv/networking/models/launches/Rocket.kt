package com.pv.networking.models.launches

data class Rocket(
        val rocket_id: String? = "",
        val rocket_name: String? = "",
        val rocket_type: String? = "",
        val first_stage: FirstStage,
        val second_stage: SecondStage,
        val fairings: Fairings
)