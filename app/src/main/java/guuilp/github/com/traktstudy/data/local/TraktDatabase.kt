package guuilp.github.com.traktstudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import guuilp.github.com.traktstudy.data.remote.entities.Show
import guuilp.github.com.traktstudy.data.remote.entities.TrendingShow

@Database(entities = [Show::class, Show::class], version = 1, exportSchema = false)
abstract class TraktDatabase : RoomDatabase() {
    abstract fun traktDAO(): TraktDAO
}