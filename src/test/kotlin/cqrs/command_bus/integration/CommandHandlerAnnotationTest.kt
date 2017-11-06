package cqrs.command_bus.integration

import cqrs.command_bus.stub.TestCommand
import cqrs.domain.command.Command
import cqrs.domain.command.CommandBus
import cqrs.infrastructure.command_bus.CommandBusAxon
import cqrs.infrastructure.command_bus.spring.AnnotationCommandHandlerDriven
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.spring.config.EnableAxon
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.inject.Inject

@ExtendWith(SpringExtension::class)
@ContextConfiguration
class CommandHandlerAnnotationTest {
    @Inject
    private lateinit var commandGateway: CommandGateway
    private val commandBus: CommandBus by lazy { CommandBusAxon(commandGateway) }

    @BeforeEach
    fun setUp() {
        TestCommandHandler.clearCommands()
    }

    @Test
    fun `it should handle a command correctly`() {
        TestCommand()
                .also { commandBus.handle(it) }
                .let {
                    assertEquals(TestCommandHandler.commands().size, 1)
                    assertEquals(TestCommandHandler.commands().first(), it)
                }

    }

    @EnableAxon
    @AnnotationCommandHandlerDriven
    @Scope
    @Configuration
    open class Context {
        @Bean
        open fun testCommandHandler(): TestCommandHandler = TestCommandHandler()
    }

    open class TestCommandHandler {
        @CommandHandler
        fun on(command: TestCommand) {
            commands.add(command)
        }

        companion object {
            private val commands = mutableListOf<Command>()
            fun commands(): List<Command> = this.commands.toList()
            fun clearCommands() {
                commands.clear()
            }
        }
    }
}
