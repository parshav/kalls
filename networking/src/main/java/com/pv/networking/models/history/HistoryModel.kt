package com.pv.networking.models.history

data class HistoryModel(
        val id: Int,
        val title: String,
        val event_date_utc: String,
        val event_date_unix: Int,
        val flight_number: Int,
        val details: String,
        val links: Links
)