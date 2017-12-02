package com.kotato.cqrs.application

import com.kotato.cqrs.infrastructure.command_bus.spring.AnnotationCommandHandlerDriven
import com.kotato.cqrs.infrastructure.query_bus.spring.AnnotationQueryHandlerDriven
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@AnnotationQueryHandlerDriven
@AnnotationCommandHandlerDriven
@Import(CqrsConfiguration::class)
annotation class EnableCqrs
