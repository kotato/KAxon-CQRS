package cqrs.infrastructure.query_bus.spring

import cqrs.domain.query.QueryHandler
import org.axonframework.commandhandling.AnnotationCommandHandlerAdapter
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.commandhandling.SupportedCommandNamesAware
import org.axonframework.messaging.MessageHandler
import org.axonframework.messaging.annotation.ParameterResolverFactory
import org.axonframework.spring.config.AbstractAnnotationHandlerBeanPostProcessor
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Method
import java.util.concurrent.atomic.AtomicBoolean

class AnnotationQueryHandlerBeanPostProcessor :
        AbstractAnnotationHandlerBeanPostProcessor<MessageHandler<CommandMessage<*>>, AnnotationCommandHandlerAdapter>() {

    override fun getAdapterInterfaces(): Array<Class<*>> =
        arrayOf(MessageHandler::class.java, SupportedCommandNamesAware::class.java)

    override fun isPostProcessingCandidate(targetClass: Class<*>): Boolean =
        hasQueryHandlerAnnotationOnAnyMethod(targetClass)

    override fun initializeAdapterFor(bean: Any,
                                      parameterResolverFactory: ParameterResolverFactory): AnnotationCommandHandlerAdapter
            = AnnotationCommandHandlerAdapter(bean, parameterResolverFactory)

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
