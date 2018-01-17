package com.kotato.cqrs.infrastructure.query_bus

import com.kotato.cqrs.domain.query.Query
import com.kotato.cqrs.domain.query.QueryBus
import org.axonframework.queryhandling.QueryGateway
import java.util.concurrent.ExecutionException
import kotlin.reflect.KClass

class QueryBusAxon(private val gateway: QueryGateway) : QueryBus {
    override fun <T> ask(query: Query, klass: KClass<*>): T {
        @Suppress("UNCHECKED_CAST")
        try {
            return gateway.send(query, klass.javaPrimitiveType ?: klass.java).get() as T
        } catch (e: ExecutionException) {
            throw e.cause!!
        }
    }
}
