package com.example.android.eventmap.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.eventmap.view.main.type.MapKindType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    fun onMapSelectingNavClickListener() {
        event(Event.MapSettingOpenEvent)
    }

    fun onMapClickListener(map: MapKindType) {
        event(Event.KindOfMapClickEvent(map))
    }

//    private val selectedMap = MediatorLiveData<Boolean>().apply {
//        addSource() {
//            value = it
//        }
//        addSource() {
//            value = it
//        }
//    }


    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: Event) {
        scope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object MapSettingOpenEvent : Event()
        data class KindOfMapClickEvent(val kind: MapKindType) : Event()
    }
}