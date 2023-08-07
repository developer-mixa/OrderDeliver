package com.example.orderdeliver.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.example.navigation.Event
import com.example.orderdeliver.share
import com.example.orderdeliver.showLog
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TapToMenuUseCase @Inject constructor() {

   private val _isTapMainFlow  = MutableSharedFlow<Event<Boolean>>(
       replay = 0,
       extraBufferCapacity = 1,
       onBufferOverflow = BufferOverflow.DROP_OLDEST
   )

    fun listen(): Flow<Event<Boolean>> = _isTapMainFlow

    suspend fun toMain() {
        showLog("toMain")
        _isTapMainFlow.emit(Event(true))
    }

}