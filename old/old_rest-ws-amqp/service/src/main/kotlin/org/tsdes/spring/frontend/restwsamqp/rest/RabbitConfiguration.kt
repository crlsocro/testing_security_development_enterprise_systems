package org.tsdes.spring.frontend.restwsamqp.rest

import org.springframework.amqp.core.FanoutExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Besides specifying the @Bean in the
 * @SpringBootApplication annotated class, we can
 * have @Configuration beans.
 *
 * Created by arcuri82 on 09-Aug-17.
 */
@Configuration
class RabbitConfiguration {

    @Bean
    fun fanout(): FanoutExchange {
        return FanoutExchange("number-of-rest-calls")
    }
}