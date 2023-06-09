package com.shoebill.maru.model.data

data class Spot(
    val id: Long = 0L,
    val landmarkId: Long? = null,
    val imageUrl: String = "https://picsum.photos/id/1/1000/2000",
    val likeCount: Int = 0,
    val tags: List<Tag>? = null,
    var liked: Boolean = false,
    var scraped: Boolean = false,
    val coordinate: Coordinate = Coordinate()
) {
    fun toggleLikeState() {
        this.liked = !this.liked
    }

    fun toggleScrapState() {
        this.scraped = !this.scraped
    }
}

data class Tag(
    val id: Long? = null,
    val name: String,
)