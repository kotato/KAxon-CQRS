package cqrs.infrastructure.command_bus

import cqrs.domain.command.Command
import cqrs.domain.command.CommandBus
import org.axonframework.commandhandling.gateway.CommandGateway

class CommandBusAxon(private val gateway: CommandGateway): CommandBus {
    override fun handle(command: Command) {
        gateway.sendAndWait<Command>(command)
    }
}
