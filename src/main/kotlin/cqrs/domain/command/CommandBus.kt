package cqrs.domain.command

interface CommandBus {
    fun handle(command: Command)
}