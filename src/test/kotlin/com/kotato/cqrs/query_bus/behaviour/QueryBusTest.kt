package com.kotato.cqrs.query_bus.behaviour

import com.github.javafaker.Faker
import com.kotato.cqrs.domain.query.Query
import com.kotato.cqrs.infrastructure.query_bus.QueryBusAxon
import com.kotato.cqrs.query_bus.stub.TestQuery
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.NoHandlerForCommandException
import org.axonframework.commandhandling.SimpleCommandBus
import org.axonframework.commandhandling.gateway.DefaultCommandGateway
import org.axonframework.messaging.MessageHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class QueryBusTest {
    private val simpleCommandBus = SimpleCommandBus()
    private val gateway = DefaultCommandGateway(simpleCommandBus)
    private val queryBus = QueryBusAxon(gateway)

    @BeforeEach
    fun setUp() {
        TestQueryHandler.clearQueries()
    }

    @Test
    fun `it should send a command and fail because no handlers`() {
        assertFailsWith<NoHandlerForCommandException> { queryBus.ask<String>(TestQuery()) }
    }

    @Test
    fun `it should send a query and handle successfully`() {
        Faker().pokemon().name()
                .also { simpleCommandBus.subscribe(TestQuery::class.java.name, TestQueryHandler(it)) }
                .let { expectedResponse ->
                    TestQuery()
                            .also { assertEquals(expectedResponse, queryBus.ask<String>(it)) }
                            .let {
                                assertEquals(1, TestQueryHandler.queries().size)
                                assertEquals(it, TestQueryHandler.queries().first())
                            }

                }
    }

    private class TestQueryHandler<out T>(private val response: T) : MessageHandler<CommandMessage<*>> {
        override fun handle(message: CommandMessage<*>): T {
            queries.add(message.payload as Query)
            return response
        }

        companion object {
            private val queries = mutableListOf<Query>()
            fun queries(): List<Query> = this.queries.toList()
            fun clearQueries() {
                this.queries.clear()
            }
        }
    }
}
