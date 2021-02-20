package vanson.dev.movieapp

class Movie(
    title: String,
    description: String = "",
    thumbnail: Int,
    studio: String = "",
    rating: String = "",
    streamingLink: String = "",
    coverPhoto: Int
) {
    private var title: String = title
    private var description: String = description
    private var thumbnail: Int = thumbnail
    private var studio: String = studio
    private var rating: String = rating
    private var streamingLink: String = streamingLink
    private var coverPhoto: Int = coverPhoto
    fun getTitle() = this.title
    fun getDescription() = this.description
    fun getThumbnail() = this.thumbnail
    fun getStudio() = this.studio
    fun getRating() = this.rating
    fun getStreamingLink() = this.streamingLink
    fun getCoverPhoto() = this.coverPhoto

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setThumbnail(thumbnail: Int) {
        this.thumbnail = thumbnail
    }

    fun setStudio(studio: String) {
        this.studio = studio
    }

    fun setRating(rating: String) {
        this.rating = rating
    }

    fun setStreamingLink(streamingLink: String) {
        this.streamingLink = streamingLink
    }

    fun setCoverPhoto(coverPhoto: Int) {
        this.coverPhoto = coverPhoto
    }
}