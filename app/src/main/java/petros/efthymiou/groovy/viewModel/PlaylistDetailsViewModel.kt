package petros.efthymiou.groovy.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import petros.efthymiou.groovy.data.model.Playlist
import petros.efthymiou.groovy.data.model.PlaylistDetails
import petros.efthymiou.groovy.data.model.PlaylistRaw
import petros.efthymiou.groovy.repository.PlaylistRepository

class PlaylistDetailsViewModel @ViewModelInject constructor(
    private val repository: PlaylistRepository
) : ViewModel() {

    val wishlist = MutableLiveData<List<Playlist>>()
    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()
    val loader = MutableLiveData<Boolean>()
    val isWishlisted = MutableLiveData<Boolean>().apply { value = false }

    init {
        viewModelScope.launch {
            repository.getWishlist().collect {
                wishlist.value = it.getOrNull()
            }
        }
    }

    fun getPlaylistDetails(id: Int) {
        viewModelScope.launch {
            loader.value = true
            repository.getPlaylistDetails(id)
                .onEach {
                    loader.value = false
                }
                .collect {
                    playlistDetails.value = it
                }
        }
    }

    fun handleWishlist(playlistDetails: Result<PlaylistDetails>) {
        viewModelScope.launch {
            val temp = PlaylistRaw(
                playlistDetails.getOrNull()!!.id.toInt(),
                playlistDetails.getOrNull()!!.name,
                playlistDetails.getOrNull()!!.category
            )

            if (isWishlisted.value!!) {
                deleteFromWishlist(temp)
                isWishlisted.value = false
            } else {
                addToWishlist(temp)
                isWishlisted.value = true
            }
        }
    }

    private suspend fun deleteFromWishlist(
        temp: PlaylistRaw
    ) {
        repository.deleteWishlist(temp)
            .collect {}
    }

    private suspend fun addToWishlist(
        temp: PlaylistRaw
    ) {
        repository.addWishlist(temp)
            .collect {}
    }

    fun checkWishlisted(playlistDetails: PlaylistDetails) {
        wishlist.value?.forEach { playlist ->
            if (playlist.name == playlistDetails.name) {
                isWishlisted.value = true
            }
        }
    }
}