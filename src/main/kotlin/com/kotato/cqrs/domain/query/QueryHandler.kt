package com.kotato.cqrs.domain.query

import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.annotation.MessageHandler
import org.axonframework.queryhandling.QueryMessage

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.ANNOTATION_CLASS)
@MessageHandler(messageType = QueryMessage::class)
@org.axonframework.queryhandling.QueryHandler
annotation class QueryHandler
