package com.kotato.cqrs.infrastructure.query_bus

import com.kotato.cqrs.domain.query.Query
import com.kotato.cqrs.domain.query.QueryBus
import org.axonframework.commandhandling.gateway.CommandGateway

class QueryBusAxon(private val gateway: CommandGateway): QueryBus {
    override fun <T> ask(query: Query): T {
        return gateway.sendAndWait(query)
    }
}
