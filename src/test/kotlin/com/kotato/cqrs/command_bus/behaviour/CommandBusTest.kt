package com.kotato.cqrs.command_bus.behaviour

import com.kotato.cqrs.command_bus.stub.TestCommand
import com.kotato.cqrs.domain.command.Command
import com.kotato.cqrs.infrastructure.command_bus.CommandBusAxon
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.NoHandlerForCommandException
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.messaging.MessageHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class CommandBusTest {
    private val simpleCommandBus = SimpleCommandBus()
    private val gateway = DefaultCommandGateway(simpleCommandBus)
    private val commandBus = CommandBusAxon(gateway)

    @BeforeEach
    fun setUp() {
        TestCommandHandler.clearCommands()
    }

    @Test
    fun `it should send a command and fail because no handlers`() {
        assertFailsWith<NoHandlerForCommandException> { commandBus.handle(TestCommand()) }
    }

    @Test
    fun `it should send a command and handle successfully`() {
        TestCommand()
                .also { simpleCommandBus.subscribe(it::class.java.name, TestCommandHandler()) }
                .also { commandBus.handle(it) }
                .let {
                    assertEquals(1, TestCommandHandler.commands().size)
                    assertEquals(it, TestCommandHandler.commands().first())
                }
    }

    private class TestCommandHandler : MessageHandler<CommandMessage<*>> {
        override fun handle(message: CommandMessage<*>): Any =
                message.also { commands.add(it.payload as Command) }

        companion object {
            private val commands = mutableListOf<Command>()
            fun commands(): List<Command> = this.commands.toList()
            fun clearCommands() {
                this.commands.clear()
            }
        }
    }
}


