package com.pv.networking.models.history

data class HistoryModel(
        val id: Int? = 0,
        val title: String? = "",
        val event_date_utc: String? = "",
        val event_date_unix: Int? = 0,
        val flight_number: Int? = 0,
        val details: String? = "",
        val links: Links
)