package com.genuine.leone.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.genuine.leone.App
import com.genuine.leone.databinding.FragmentFavoriteChannelsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteChannelsFragment : Fragment() {

    private var _binding: FragmentFavoriteChannelsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteChannelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteChannelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteChannelAdapter { favorite ->
            viewLifecycleOwner.lifecycleScope.launch {
                App.instance.database.favoriteChannelDao().delete(favorite)
            }
        }
        binding.recyclerFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavorites.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            App.instance.database.favoriteChannelDao().getByPlaylist(ACTIVE_PLAYLIST_PLACEHOLDER)
                .collectLatest { list -> adapter.submitList(list) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ACTIVE_PLAYLIST_PLACEHOLDER = ""
    }
}
