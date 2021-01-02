package petros.efthymiou.groovy.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.data.model.PlaylistDetails
import petros.efthymiou.groovy.data.model.PlaylistRaw
import java.lang.RuntimeException
import javax.inject.Inject


class PlaylistService @Inject constructor(
    private val api: PlaylistApi
) {

    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistRaw>>> {
        return flow {
            emit(Result.success(api.fetchAllPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

    suspend fun fetchPlaylistDetails(id: Int): Flow<Result<PlaylistDetails>> {
        return flow {
            emit(Result.success(api.fetchAllPlaylistDetails(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}