package guuilp.github.com.traktstudy.data.local

import androidx.room.Insert
import androidx.room.Query
import guuilp.github.com.traktstudy.data.local.entities.Show
import guuilp.github.com.traktstudy.data.remote.entities.TrendingShow

interface TraktDAO {
    @Insert
    fun saveShows(trendingShows: List<Show>)

    @Insert
    fun saveShow(trendingShow: Show)

    @Query("SELECT * FROM show WHERE id = :id")
    fun findTrendingShowById(id: Long): TrendingShow

    @Query("SELECT * FROM show")
    fun findAllTrendingShows(): List<TrendingShow>

    @Query("SELECT * FROM show WHERE trending = 1")
    fun findTrendingShows(): List<TrendingShow>
}