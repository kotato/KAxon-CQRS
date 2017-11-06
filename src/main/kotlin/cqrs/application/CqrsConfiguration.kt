package cqrs.application

import cqrs.domain.command.CommandBus
import cqrs.infrastructure.command_bus.CommandBusAxon
import cqrs.domain.query.QueryBus
import cqrs.infrastructure.query_bus.QueryBusAxon
import org.axonframework.commandhandling.gateway.CommandGateway
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
    open fun cqrsQueryBus(commandGateway: CommandGateway): QueryBus =
        QueryBusAxon(commandGateway)
}