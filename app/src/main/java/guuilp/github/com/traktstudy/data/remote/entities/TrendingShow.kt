package guuilp.github.com.traktstudy.data.remote.entities

import androidx.room.Embedded

data class TrendingShow(
    @Embedded val show: Show,
    @Embedded val watchers: Int
)