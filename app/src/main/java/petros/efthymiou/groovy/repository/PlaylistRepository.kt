package petros.efthymiou.groovy.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import petros.efthymiou.groovy.data.model.PlaylistDetails
import petros.efthymiou.groovy.data.model.Playlist
import petros.efthymiou.groovy.data.PlaylistMapper
import petros.efthymiou.groovy.data.local.WishlistDao
import petros.efthymiou.groovy.data.local.WishlistService
import petros.efthymiou.groovy.data.model.PlaylistRaw
import petros.efthymiou.groovy.data.remote.PlaylistService
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val remoteService: PlaylistService,
    private val localService: WishlistService,
    private val mapper: PlaylistMapper
) {

    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> =
        remoteService.fetchPlaylists().map {
            if (it.isSuccess)
                Result.success(mapper(it.getOrNull()!!))
            else
                Result.failure(it.exceptionOrNull()!!)
        }

    suspend fun getPlaylistDetails(id: Int): Flow<Result<PlaylistDetails>> =
        remoteService.fetchPlaylistDetails(id).map {
            if (it.isSuccess)
                Result.success(it.getOrNull()!!)
            else
                Result.failure(it.exceptionOrNull()!!)
        }

    suspend fun getWishlist(): Flow<Result<List<Playlist>>> =
        localService.getWishlists().map {
            if (it.isSuccess)
                Result.success(mapper(it.getOrNull()!!))
            else
                Result.failure(it.exceptionOrNull()!!)
        }

    suspend fun addWishlist(wishlist: PlaylistRaw): Flow<Result<Unit>> =
        localService.addWishlist(wishlist).map {
            if (it.isSuccess)
                Result.success((it.getOrNull()!!))
            else
                Result.failure(it.exceptionOrNull()!!)
        }

    suspend fun deleteWishlist(wishlist: PlaylistRaw): Flow<Result<Unit>> =
        localService.deleteWishlist(wishlist).map {
            if (it.isSuccess)
                Result.success((it.getOrNull()!!))
            else
                Result.failure(it.exceptionOrNull()!!)
        }
}