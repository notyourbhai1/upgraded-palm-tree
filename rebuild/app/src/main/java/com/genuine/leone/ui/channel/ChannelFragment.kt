package com.genuine.leone.ui.channel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.genuine.leone.App
import com.genuine.leone.databinding.FragmentChannelBinding
import com.genuine.leone.model.ContentBean
import com.genuine.leone.model.Stream
import com.genuine.leone.player.PlayerActivity
import com.genuine.leone.utils.M3uParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ChannelFragment : Fragment() {

    private var _binding: FragmentChannelBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ChannelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChannelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChannelAdapter { openPlayer(it) }
        binding.recyclerChannels.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerChannels.adapter = adapter

        loadActivePlaylist()
    }

    private fun loadActivePlaylist() {
        viewLifecycleOwner.lifecycleScope.launch {
            val playlist = App.instance.database.playlistDao().getById(1) ?: return@launch
            val channels = withContext(Dispatchers.IO) {
                runCatching {
                    URL(playlist.playlistUrl).openStream().use { M3uParser.parse(it) }
                }.getOrDefault(emptyList())
            }
            adapter.submitList(channels)
        }
    }

    private fun openPlayer(content: ContentBean) {
        val stream = Stream(
            mediaStreamUrl = content.streamUrl,
            cookieValue = content.cookie,
            refererValue = content.referer,
            originValue = content.origin,
            drmLicense = content.drmLicense,
            userAgent = content.userAgent,
            drmSchema = content.drmSchema
        )
        val intent = Intent(requireContext(), PlayerActivity::class.java)
        intent.putExtra(PlayerActivity.EXTRA_STREAM, stream)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
