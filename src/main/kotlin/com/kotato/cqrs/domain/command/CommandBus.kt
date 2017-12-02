package com.kotato.cqrs.domain.command

interface CommandBus {
    fun handle(command: Command)
}