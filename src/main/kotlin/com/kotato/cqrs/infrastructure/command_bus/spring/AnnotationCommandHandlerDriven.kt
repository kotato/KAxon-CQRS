package com.kotato.cqrs.infrastructure.command_bus.spring

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(AnnotationDrivenRegistrar::class)
annotation class AnnotationCommandHandlerDriven
