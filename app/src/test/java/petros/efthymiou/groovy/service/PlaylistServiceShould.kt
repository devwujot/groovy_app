package petros.efthymiou.groovy.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.data.remote.PlaylistApi
import petros.efthymiou.groovy.data.remote.PlaylistService
import petros.efthymiou.groovy.data.model.PlaylistDetails
import petros.efthymiou.groovy.data.model.PlaylistRaw
import petros.efthymiou.groovy.utils.BaseUnitTest

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val id = 100
    private val api: PlaylistApi = mock()
    private val playlists: List<PlaylistRaw> = mock()
    private val playlistDetails: PlaylistDetails = mock()

    @Test
    fun fetchPlaylistFromApi() = runBlockingTest {

        service = PlaylistService(api)

        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runBlockingTest {

        mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {

        mockFailureCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message
        )
    }

    @Test
    fun fetchPlaylistDetailsFromApi() = runBlockingTest {
        mockPlaylistDetailsSuccessfulCase()

        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchAllPlaylistDetails(id)
    }

    @Test
    fun convertPlaylistDetailsValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockPlaylistDetailsSuccessfulCase()

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).first())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        mockPlaylistDetailsErrorCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylistDetails(id).first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockFailureCase() {
        whenever(api.fetchAllPlaylists()).thenThrow(exception)

        service = PlaylistService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)
    }

    private suspend fun mockPlaylistDetailsSuccessfulCase() {
        whenever(api.fetchAllPlaylistDetails(id)).thenReturn(playlistDetails)

        service = PlaylistService(api)
    }

    private suspend fun mockPlaylistDetailsErrorCase() {
        whenever(api.fetchAllPlaylistDetails(id)).thenThrow(exception)

        service = PlaylistService(api)
    }
}