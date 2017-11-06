package cqrs.query_bus.integration

import cqrs.domain.query.Query
import cqrs.domain.query.QueryBus
import cqrs.domain.query.QueryHandler
import cqrs.infrastructure.query_bus.QueryBusAxon
import cqrs.infrastructure.query_bus.spring.AnnotationQueryHandlerDriven
import cqrs.query_bus.stub.TestQuery
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.spring.config.EnableAxon
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
class QueryHandlerAnnotationTest {
    @Inject
    private lateinit var commandGateway: CommandGateway
    private val queryBus: QueryBus by lazy { QueryBusAxon(commandGateway) }

    @BeforeEach
    fun setUp() {
        TestQueryHandler.resetQueries()
    }

    @Test
    fun `it should handle a query correctly`() {
        TestQuery()
                .also { queryBus.ask<Boolean>(it).let(::assertTrue) }
                .let {
                    assertEquals(TestQueryHandler.queries().size, 1)
                    assertEquals(TestQueryHandler.queries().first(), it)
                }


    }

    @EnableAxon
    @AnnotationQueryHandlerDriven
    @Scope
    @Configuration
    open class Context {
        @Bean
        open fun testQueryHandler(): TestQueryHandler = TestQueryHandler()
    }

    open class TestQueryHandler {
        @QueryHandler
        fun on(query: TestQuery): Boolean {
            queries.add(query)
            return true
        }

        companion object {
            private val queries = mutableListOf<Query>()
            fun queries(): List<Query> = this.queries.toList()
            fun resetQueries() {
                this.queries.clear()
            }
        }
    }
}
