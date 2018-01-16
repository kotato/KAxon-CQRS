package com.kotato.cqrs.application

import com.kotato.cqrs.domain.command.CommandBus
import com.kotato.cqrs.infrastructure.command_bus.CommandBusAxon
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.infrastructure.query_bus.QueryBusAxon
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CqrsConfiguration {

    @Bean
    @ConditionalOnClass(value = EnableCqrs::class)
    open fun cqrsCommandBus(commandGateway: CommandGateway): CommandBus =
        CommandBusAxon(commandGateway)

    @Bean
    @ConditionalOnClass(value = EnableCqrs::class)
    open fun cqrsQueryBus(queryGateway: QueryGateway): QueryBus =
        QueryBusAxon(queryGateway)
}