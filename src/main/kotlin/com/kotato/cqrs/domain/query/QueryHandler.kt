package com.kotato.cqrs.domain.query

import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.annotation.MessageHandler

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.ANNOTATION_CLASS)
@MessageHandler(messageType = CommandMessage::class)
@org.axonframework.commandhandling.CommandHandler
annotation class QueryHandler
