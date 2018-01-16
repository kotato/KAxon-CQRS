package com.kotato.cqrs.domain.query

import kotlin.reflect.KClass

interface QueryBus {
    fun <T> ask(query: Query, klass: KClass<*>): T
}

inline fun <reified T> QueryBus.ask(query: Query): T {
    return this.ask(query, T::class)
}