package petros.efthymiou.groovy.data.remote

import petros.efthymiou.groovy.data.model.PlaylistDetails
import petros.efthymiou.groovy.data.model.PlaylistRaw
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistApi {

    @GET("playlists")
    suspend fun fetchAllPlaylists(): List<PlaylistRaw>

    @GET("playlist-details/{id}")
    suspend fun fetchAllPlaylistDetails(@Path("id") id: Int): PlaylistDetails

}