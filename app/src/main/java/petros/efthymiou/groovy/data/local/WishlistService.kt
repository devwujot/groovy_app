package petros.efthymiou.groovy.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.data.model.PlaylistRaw
import java.lang.RuntimeException
import javax.inject.Inject

class WishlistService @Inject constructor(
    private val dao: WishlistDao
) {

    suspend fun getWishlists(): Flow<Result<List<PlaylistRaw>>> {
        return flow {
            emit(Result.success(dao.getAllWishlist()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

    suspend fun addWishlist(wishlist: PlaylistRaw): Flow<Result<Unit>> {
        return flow {
            emit(Result.success(dao.insertWishlist(wishlist)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

    suspend fun deleteWishlist(wishlist: PlaylistRaw): Flow<Result<Unit>> {
        return flow {
            emit(Result.success(dao.deleteWishlist(wishlist)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}