package com.kotato.cqrs.application

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(CqrsConfiguration::class)
annotation class EnableCqrs
