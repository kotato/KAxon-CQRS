package com.kotato.cqrs.query_bus.behaviour

import com.github.javafaker.Faker
import com.kotato.cqrs.domain.query.Query
import com.kotato.cqrs.domain.query.ask
import com.kotato.cqrs.infrastructure.query_bus.QueryBusAxon
import com.kotato.cqrs.query_bus.stub.TestQuery
import org.axonframework.messaging.MessageHandler
import org.axonframework.queryhandling.DefaultQueryGateway
import org.axonframework.queryhandling.NoHandlerForQueryException
import org.axonframework.queryhandling.QueryMessage
import org.axonframework.queryhandling.SimpleQueryBus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.ExecutionException
import kotlin.test.assertFailsWith

class QueryBusTest {
    private val simpleQueryBus = SimpleQueryBus()
    private val gateway = DefaultQueryGateway(simpleQueryBus)
    private val queryBus = QueryBusAxon(gateway)

    @BeforeEach
    fun setUp() {
        TestQueryHandler.clearQueries()
    }

    @Test
    fun `it should send a command and fail because no handlers`() {
        assertFailsWith<ExecutionException> { queryBus.ask<String>(TestQuery()) }
                .let { assertEquals(it.cause!!::class, NoHandlerForQueryException::class) }
    }

    @Test
    fun `it should send a query and handle successfully`() {
        Faker().pokemon().name()
                .also { simpleQueryBus.subscribe(TestQuery::class.java.name, it::class.java, TestQueryHandler(it)) }
                .let { expectedResponse ->
                    TestQuery()
                            .also { assertEquals(expectedResponse, queryBus.ask<String>(it)) }
                            .let {
                                assertEquals(1, TestQueryHandler.queries().size)
                                assertEquals(it, TestQueryHandler.queries().first())
                            }

                }
    }

    private class TestQueryHandler<out T>(private val response: T) : MessageHandler<QueryMessage<*, *>> {
        override fun handle(message: QueryMessage<*, *>): T {
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
