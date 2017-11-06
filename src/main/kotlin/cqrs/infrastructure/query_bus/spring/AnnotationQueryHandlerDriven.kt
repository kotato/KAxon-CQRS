package cqrs.infrastructure.query_bus.spring

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(AnnotationDrivenRegistrar::class)
annotation class AnnotationQueryHandlerDriven
