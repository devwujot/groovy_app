package petros.efthymiou.groovy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_raw")
data class PlaylistRaw(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val category: String
) {

}