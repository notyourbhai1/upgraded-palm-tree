package com.genuine.leone.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.genuine.leone.R
import com.genuine.leone.databinding.ActivityMainBinding
import com.genuine.leone.ui.channel.ChannelFragment
import com.genuine.leone.ui.favorite.FavoriteChannelsFragment
import com.genuine.leone.ui.home.HomeFragment
import com.genuine.leone.ui.playlist.PlaylistFragment
import com.genuine.leone.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_playlist -> PlaylistFragment()
                R.id.nav_channels -> ChannelFragment()
                R.id.nav_favorites -> FavoriteChannelsFragment()
                R.id.nav_settings -> SettingFragment()
                else -> HomeFragment()
            }
            showFragment(fragment)
            true
        }

        if (savedInstanceState == null) {
            showFragment(HomeFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
