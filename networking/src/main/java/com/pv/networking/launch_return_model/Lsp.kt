package com.pv.networking.launch_return_model

data class Lsp(
        val id: Int,
        val name: String,
        val abbrev: String,
        val countryCode: String,
        val type: Int,
        val infoURL: String? = "",
        val wikiURL: String,
        val changed: String,
        val infoURLs: List<String>
)