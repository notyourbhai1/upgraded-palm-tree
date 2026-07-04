package com.genuine.leone.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.genuine.leone.App
import com.genuine.leone.data.entities.PlaylistEntity
import com.genuine.leone.databinding.FragmentPlaylistBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PlaylistFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlaylistAdapter { addPlaylistFromInput() }
        binding.recyclerPlaylists.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPlaylists.adapter = adapter

        binding.buttonAddPlaylist.setOnClickListener { addPlaylistFromInput() }

        viewLifecycleOwner.lifecycleScope.launch {
            App.instance.database.playlistDao().getAll().collectLatest { list ->
                adapter.submitList(list)
            }
        }
    }

    private fun addPlaylistFromInput() {
        val url = binding.inputPlaylistUrl.text?.toString()?.trim().orEmpty()
        val name = binding.inputPlaylistName.text?.toString()?.trim().orEmpty()
        if (url.isEmpty() || name.isEmpty()) return

        viewLifecycleOwner.lifecycleScope.launch {
            App.instance.database.playlistDao().insert(
                PlaylistEntity(
                    playlistUrl = url,
                    playlistName = name,
                    userAgent = PlaylistAdapter.DEFAULT_USER_AGENT,
                    sortNum = 0
                )
            )
            binding.inputPlaylistUrl.text?.clear()
            binding.inputPlaylistName.text?.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
