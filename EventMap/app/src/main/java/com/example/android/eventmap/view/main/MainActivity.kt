package com.example.android.eventmap.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android.eventmap.R
import com.example.android.eventmap.databinding.ActivityMainBinding
import com.example.android.eventmap.util.extensions.toastMessage
import com.example.android.eventmap.view.main.type.MapKindType
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Jihye Noh
 * Date: 2022-01-09
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val viewModel by viewModels<MainViewModel>()

    private var naverMap: NaverMap? = null
    private var backPressedTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = viewModel

        collectFlows()
    }

    override fun onDestroy() {
        super.onDestroy()
        naverMap = null
    }

    override fun onBackPressed() {
        closeNaviMapKindDrawable()
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
    }

    private fun collectFlows() {
        lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->
                handleEvent(event)
            }

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedMap.collect {
                    when (it) {
                        MapKindType.BASIC -> {
                            naverMap?.mapType = NaverMap.MapType.Basic
                        }
                        MapKindType.SATELLITE -> {
                            naverMap?.mapType = NaverMap.MapType.Satellite
                        }
                        MapKindType.TERRAIN -> {
                            naverMap?.mapType = NaverMap.MapType.Terrain
                        }
                        MapKindType.NAVI -> {
                            naverMap?.mapType = NaverMap.MapType.Navi
                        }
                    }
                }
            }
        }
    }

    private fun handleEvent(event: MainViewModel.Event) = when (event) {
        is MainViewModel.Event.MapSettingOpenEvent -> {
            binding.mainDrawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun closeNaviMapKindDrawable() {
        if (binding.mainDrawerLayout.isDrawerOpen(binding.navigationviewSetting)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            return
        }

        val currentTime = System.currentTimeMillis()
        when (currentTime - backPressedTime) {
            in 0..2000 -> {
                super.onBackPressed()
            }
            else -> {
                backPressedTime = currentTime
                toastMessage(R.string.one_press_back_button_toast)
            }
        }
    }
}