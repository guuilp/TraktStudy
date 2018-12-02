package guuilp.github.com.traktstudy.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Show(
    @PrimaryKey(autoGenerate = true) val id: Long = -1,
    @Embedded val ids: Ids,
    val title: String,
    val year: Int,
    val trending: Int
)