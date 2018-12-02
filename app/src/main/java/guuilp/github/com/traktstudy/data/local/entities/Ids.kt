package guuilp.github.com.traktstudy.data.local.entities

data class Ids(
    val imdb: String,
    val slug: String? = null,
    val tmdb: Int? = null,
    val trakt: Int? = null,
    val tvdb: Int? = null,
    val tvrage: Any? = null
)