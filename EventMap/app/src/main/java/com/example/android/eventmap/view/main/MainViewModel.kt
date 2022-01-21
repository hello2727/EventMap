package com.example.android.eventmap.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.eventmap.view.main.type.MapKindType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Jihye Noh
 * Date: 2022-01-10
 */
@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {
    private val scope = viewModelScope + CoroutineExceptionHandler { _, e ->
        Timber.e(e.toString())
    }

    private val _selectedMap = MutableStateFlow(MapKindType.BASIC)
    val selectedMap: StateFlow<MapKindType> = _selectedMap.asStateFlow()

    fun onMapClickListener(map: MapKindType) {
        _selectedMap.value = map
    }

//    private val selectedMap = MediatorLiveData<Boolean>().apply {
//        addSource() {
//            value = it
//        }
//        addSource() {
//            value = it
//        }
//    }

    sealed class Event {
        object selectMapEvent : Event()
    }
}