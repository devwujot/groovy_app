package petros.efthymiou.groovy.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import petros.efthymiou.groovy.R
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist_detail.*
import petros.efthymiou.groovy.data.model.PlaylistDetails
import petros.efthymiou.groovy.viewModel.PlaylistDetailsViewModel

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private val viewModel: PlaylistDetailsViewModel by viewModels()
    val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        val id = args.playlistId
        viewModel.getPlaylistDetails(id)
        observeLiveData()
        return view
    }

    private fun observeLiveData() {
        viewModel.playlistDetails.observe(viewLifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                setupUI(playlistDetails)
                viewModel.checkWishlisted(playlistDetails.getOrNull()!!)
            } else {
                Snackbar.make(
                    playlist_details_root,
                    R.string.generic_error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        viewModel.loader.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> details_loader.visibility = View.VISIBLE
                else -> details_loader.visibility = View.GONE
            }
        }
    }

    private fun setupUI(playlistDetails: Result<PlaylistDetails>) {
        playlist_name.text = playlistDetails.getOrNull()!!.name
        playlist_details.text = playlistDetails.getOrNull()!!.details

        button_wishlist.visibility = View.VISIBLE
        button_wishlist.setOnClickListener {
            viewModel.handleWishlist(playlistDetails)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistDetailFragment()
    }
}