package com.kotato.cqrs.infrastructure.command_bus

import com.kotato.cqrs.domain.command.Command
import com.kotato.cqrs.domain.command.CommandBus
import org.axonframework.commandhandling.gateway.CommandGateway

class CommandBusAxon(private val gateway: CommandGateway): CommandBus {
    override fun handle(command: Command) {
        gateway.sendAndWait<Command>(command)
    }
}
