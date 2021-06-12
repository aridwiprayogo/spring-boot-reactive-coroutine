package com.example.springbootreactivecouroutine

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.data.redis.connection.ReactiveSubscription
import org.springframework.data.redis.core.*
import org.springframework.stereotype.Repository

@Repository
class CounterRepository(
    private val redisTemplate: ReactiveRedisTemplate<String, CounterEvent>
) {
    suspend fun get(): CounterState =
        CounterState(redisTemplate.opsForValue().incrementAndAwait(COUNTER_KEY, 0L))

    suspend fun up(): CounterState =
        CounterState(redisTemplate.opsForValue().incrementAndAwait(COUNTER_KEY)).also {
            redisTemplate.sendAndAwait(COUNTER_CHANNEL, it.toEvent(CounterAction.UP))
        }

    suspend fun down(): CounterState =
        CounterState(redisTemplate.opsForValue().decrementAndAwait(COUNTER_KEY)).also {
            redisTemplate.sendAndAwait(COUNTER_CHANNEL, it.toEvent(CounterAction.DOWN))
        }

    suspend fun stream(): Flow<CounterEvent> =
        redisTemplate.listenToChannelAsFlow(COUNTER_CHANNEL).map { message: ReactiveSubscription.Message<String, CounterEvent> ->
            message.message
        }

    companion object{
        private const val COUNTER_CHANNEL = "COUNTER CHANNEL"
        private const val COUNTER_KEY = "COUNTER"
    }
}