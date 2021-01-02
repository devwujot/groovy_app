package petros.efthymiou.groovy.data.local

import androidx.room.*
import petros.efthymiou.groovy.data.model.PlaylistRaw

@Dao
interface WishlistDao {

    @Query("SELECT * FROM playlist_raw")
    suspend fun getAllWishlist(): List<PlaylistRaw>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlist(wishlist: PlaylistRaw)

    @Delete
    suspend fun deleteWishlist(wishlist: PlaylistRaw)
}