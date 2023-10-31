package com.example.orderdeliver

import com.example.orderdeliver.utils.showLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.junit.Test
import java.lang.Exception

class Test {

    @Test
    fun main(){
        val channel: Channel<Any>? = null

        GlobalScope.launch {
            try {
                channel!!

                channel.send(1)
            }catch (e: Exception){

            }
        }
    }


}