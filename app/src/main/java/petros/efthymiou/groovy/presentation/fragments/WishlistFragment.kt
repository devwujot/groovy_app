package petros.efthymiou.groovy.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist.loader
import kotlinx.android.synthetic.main.fragment_wishlist.*
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.data.model.Playlist
import petros.efthymiou.groovy.presentation.adapters.MyPlaylistRecyclerViewAdapter
import petros.efthymiou.groovy.viewModel.WishlistViewModel

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private val viewModel: WishlistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData(view)
    }

    private fun observeLiveData(view: View) {
        viewModel.loader.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> wishlist_loader.visibility = View.VISIBLE
                else -> wishlist_loader.visibility = View.GONE
            }
        }

        viewModel.wishlist.observe(viewLifecycleOwner) { wishlist ->
            if (wishlist.getOrNull() != null) {
                setupList(view.findViewById(R.id.wishlist_list), wishlist.getOrNull()!!)
            } else {
                //
            }
        }
    }

    private fun setupList(
        view: View?,
        playlists: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(playlists) { id ->
                val action =
                    WishlistFragmentDirections.actionWishlistFragmentToPlaylistDetailFragment(id)
                findNavController().navigate(action)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WishlistFragment()
    }
}
