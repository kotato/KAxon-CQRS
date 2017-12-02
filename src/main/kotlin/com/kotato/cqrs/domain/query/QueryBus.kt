package com.kotato.cqrs.domain.query

interface QueryBus {
    fun <T> ask(query: Query): T
}

