package com.example.android.eventmap.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.eventmap.R
import com.example.android.eventmap.databinding.ActivityMainBinding
import com.example.android.eventmap.databinding.NavigationheaderSettingBinding
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Jihye Noh
 * Date: 2022-01-09
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    private val naviMapBinding : NavigationheaderSettingBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.navigationheader_setting)
    }

    private val viewModel by viewModels<MainViewModel>()

    private var naverMap: NaverMap? = null
        set(value) {
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = viewModel
        naviMapBinding.model = viewModel
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
    }
}