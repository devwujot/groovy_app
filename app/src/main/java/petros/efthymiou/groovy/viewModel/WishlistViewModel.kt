package petros.efthymiou.groovy.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.onEach
import petros.efthymiou.groovy.repository.PlaylistRepository

class WishlistViewModel @ViewModelInject constructor(
    private val repository: PlaylistRepository
) : ViewModel() {

    val wishlist = liveData {
        loader.value = true
        emitSource(repository.getWishlist()
            .onEach {
                loader.value = false
            }
            .asLiveData())
    }

    val loader = MutableLiveData<Boolean>()
}