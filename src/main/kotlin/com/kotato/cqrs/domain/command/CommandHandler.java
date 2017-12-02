package com.kotato.cqrs.domain.command;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.annotation.MessageHandler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@MessageHandler(messageType = CommandMessage.class)
@org.axonframework.commandhandling.CommandHandler
public @interface CommandHandler {}
