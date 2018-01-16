package com.kotato.cqrs.infrastructure.query_bus.spring

import com.kotato.cqrs.domain.query.QueryHandler
import org.axonframework.messaging.MessageHandler
import org.axonframework.messaging.annotation.ParameterResolverFactory
import org.axonframework.queryhandling.QueryHandlerAdapter
import org.axonframework.queryhandling.QueryMessage
import org.axonframework.queryhandling.annotation.AnnotationQueryHandlerAdapter
import org.axonframework.spring.config.AbstractAnnotationHandlerBeanPostProcessor
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Method
import java.util.concurrent.atomic.AtomicBoolean

class AnnotationQueryHandlerBeanPostProcessor :
        AbstractAnnotationHandlerBeanPostProcessor<MessageHandler<QueryMessage<*, *>>, AnnotationQueryHandlerAdapter<*>>() {

    override fun getAdapterInterfaces(): Array<Class<*>> =
            arrayOf(MessageHandler::class.java, QueryHandlerAdapter::class.java)

    override fun isPostProcessingCandidate(targetClass: Class<*>): Boolean =
            hasQueryHandlerAnnotationOnAnyMethod(targetClass)

    override fun initializeAdapterFor(bean: Any,
                                      parameterResolverFactory: ParameterResolverFactory): AnnotationQueryHandlerAdapter<*>
            = AnnotationQueryHandlerAdapter(bean, parameterResolverFactory)

    private fun hasQueryHandlerAnnotationOnAnyMethod(beanClass: Class<*>): Boolean =
            AtomicBoolean(false)
                    .also { ReflectionUtils.doWithMethods(beanClass, HasEventHandlerAnnotationMethodCallback(it)) }
                    .get()

    private class HasEventHandlerAnnotationMethodCallback constructor(private val result: AtomicBoolean) : ReflectionUtils.MethodCallback {
        @Throws(IllegalArgumentException::class, IllegalAccessException::class)
        override fun doWith(method: Method) {
            if (method.isAnnotationPresent(QueryHandler::class.java)) {
                result.set(true)
            }
        }
    }
}
