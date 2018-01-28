package com.kotato.cqrs.application

import com.kotato.cqrs.domain.command.CommandBus
import com.kotato.cqrs.infrastructure.command_bus.CommandBusAxon
import com.kotato.cqrs.domain.query.QueryBus
import com.kotato.cqrs.infrastructure.command_bus.spring.AnnotationCommandHandlerBeanPostProcessor
import com.kotato.cqrs.infrastructure.query_bus.QueryBusAxon
import com.kotato.cqrs.infrastructure.query_bus.spring.AnnotationQueryHandlerBeanPostProcessor
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.annotation.ParameterResolverFactory
import org.axonframework.queryhandling.QueryGateway
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class CqrsConfiguration {

    @Bean
    @ConditionalOnClass(value = [(EnableCqrs::class)])
    open fun cqrsCommandBus(commandGateway: CommandGateway): CommandBus =
        CommandBusAxon(commandGateway)

    @Bean
    @ConditionalOnClass(value = [(EnableCqrs::class)])
    open fun cqrsQueryBus(queryGateway: QueryGateway): QueryBus =
        QueryBusAxon(queryGateway)

    @Bean("__custom-axon-annotation-command-handler-bean-post-processor")
    @ConditionalOnClass(value = [(EnableCqrs::class)])
    open fun annotationCommandHandlerBeanPostProcessor(a: ParameterResolverFactory) =
            AnnotationCommandHandlerBeanPostProcessor()
                    .also { it.setParameterResolverFactory(a) }

    @Bean("__custom-axon-annotation-query-handler-bean-post-processor")
    @ConditionalOnClass(value = [(EnableCqrs::class)])
    open fun annotationQueryHandlerBeanPostProcessor(a: ParameterResolverFactory) =
            AnnotationQueryHandlerBeanPostProcessor()
                    .also { it.setParameterResolverFactory(a) }
}