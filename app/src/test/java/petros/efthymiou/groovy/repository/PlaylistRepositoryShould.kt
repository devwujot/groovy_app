package petros.efthymiou.groovy.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.data.PlaylistMapper
import petros.efthymiou.groovy.data.local.WishlistService
import petros.efthymiou.groovy.data.model.Playlist
import petros.efthymiou.groovy.data.model.PlaylistRaw
import petros.efthymiou.groovy.repository.PlaylistRepository
import petros.efthymiou.groovy.data.remote.PlaylistService
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistRepositoryShould : BaseUnitTest() {

    private val remoteService: PlaylistService = mock<PlaylistService>()
    private val localService: WishlistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()

    @Test
    fun getPlaylistsFromService() = runBlockingTest {
        val repository = PlaylistRepository(remoteService, localService, mapper)
        repository.getPlaylists()
        verify(remoteService, times(1)).fetchPlaylists()
    }

    @Test
    fun emitMappedPlaylistsFromService() = runBlockingTest {

        val repository = mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessfulCase()

        repository.getPlaylists().first()

        verify(mapper, times(1)).invoke(playlistsRaw)
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(remoteService.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<PlaylistRaw>>(exception))
            }
        )
        return PlaylistRepository(remoteService, localService, mapper)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(remoteService.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success<List<PlaylistRaw>>(playlistsRaw))
            }
        )

        whenever(mapper.invoke(playlistsRaw)).thenReturn(playlists)

        return PlaylistRepository(remoteService, localService, mapper)
    }
}