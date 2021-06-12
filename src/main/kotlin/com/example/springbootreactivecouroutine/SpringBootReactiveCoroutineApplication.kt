package com.example.springbootreactivecouroutine

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.newSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@SpringBootApplication
class SpringBootReactiveCoroutineApplication{
    @Bean
    fun reactiveRedisTemplate(
        connectionFactory: ReactiveRedisConnectionFactory,
        objectMapper: ObjectMapper
    ): ReactiveRedisTemplate<String, CounterEvent>{
        val serializer = Jackson2JsonRedisSerializer(CounterEvent::class.java).apply {
            setObjectMapper(objectMapper)
        }

        return ReactiveRedisTemplate(
            connectionFactory,
            newSerializationContext<String, CounterEvent>(StringRedisSerializer())
                .value(serializer)
                .build()
        )
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBootReactiveCoroutineApplication>(*args)
}
