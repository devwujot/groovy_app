package petros.efthymiou.groovy.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.onEach
import petros.efthymiou.groovy.repository.PlaylistRepository

class PlaylistViewModel @ViewModelInject constructor(
    private val repository: PlaylistRepository
) : ViewModel() {

    val playlists = liveData {
        loader.value = true
        emitSource(repository.getPlaylists()
            .onEach {
                loader.value = false
            }
            .asLiveData())
    }

    val loader = MutableLiveData<Boolean>()
}