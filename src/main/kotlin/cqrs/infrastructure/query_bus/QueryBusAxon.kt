package cqrs.infrastructure.query_bus

import cqrs.domain.query.Query
import cqrs.domain.query.QueryBus
import org.axonframework.commandhandling.gateway.CommandGateway

class QueryBusAxon(private val gateway: CommandGateway): QueryBus {
    override fun <T> ask(query: Query): T {
        return gateway.sendAndWait(query)
    }
}
