package com.pv.networking.models.launches

data class Links(
        val mission_patch: String? = "",
        val mission_patch_small: String? = "",
        val reddit_campaign: String? = "",
        val reddit_launch: String? = "",
        val reddit_recovery: String? = "",
        val reddit_media: String? = "",
        val presskit: String? = "",
        val article_link: String? = "",
        val wikipedia: String? = "",
        val video_link: String? = "",
        val flickr_images: List<String>? = emptyList()
)