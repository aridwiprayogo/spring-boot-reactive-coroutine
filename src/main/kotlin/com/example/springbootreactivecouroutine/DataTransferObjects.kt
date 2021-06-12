package com.example.springbootreactivecouroutine

import java.time.ZoneId
import java.time.ZonedDateTime

@Suppress("MemberVisibilityCanBePrivate")
class CounterState(
    val value: Long,
    val at: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"))
){
    fun toEvent(action: CounterAction) = CounterEvent(value = this.value, action = action, at = this.at)
}

enum class CounterAction{ UP, DOWN }

@Suppress("unused")
class CounterEvent(
    val value: Long,
    val action: CounterAction,
    val at: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"))
)