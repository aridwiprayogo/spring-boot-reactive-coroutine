package com.example.springbootreactivecouroutine

import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CounterController(
    private val repository: CounterRepository
) {
    @GetMapping("/")
    suspend fun get(): CounterState = repository.get()

    @PutMapping("/up")
    suspend fun up(): CounterState = repository.up()

    @PutMapping("/down")
    suspend fun down(): CounterState = repository.down()

    @GetMapping(value = ["/"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    suspend fun stream(): Flow<CounterEvent> = repository.stream()
}
