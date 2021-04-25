package vanson.dev.movieapp.models_pre_versions

class Movie(
    private var title: String,
    private var description: String = "",
    private var thumbnail: Int,
    private var studio: String = "",
    private var rating: String = "",
    private var streamingLink: String = "",
    private var coverPhoto: Int
) {
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