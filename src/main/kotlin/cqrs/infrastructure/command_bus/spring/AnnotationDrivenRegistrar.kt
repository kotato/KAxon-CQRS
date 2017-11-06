package cqrs.infrastructure.command_bus.spring

import org.axonframework.spring.config.annotation.SpringContextParameterResolverFactoryBuilder.getBeanReference
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata

class AnnotationDrivenRegistrar : ImportBeanDefinitionRegistrar {
    private val COMMAND_HANDLER_BEAN_NAME = "__axon-annotation-command-handler-bean-post-processor"

    override fun registerBeanDefinitions(importingClassMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        registerAnnotationCommandHandlerBeanPostProcessor(registry)
    }

    fun registerAnnotationCommandHandlerBeanPostProcessor(registry: BeanDefinitionRegistry) {
        GenericBeanDefinition()
                .also { it.beanClass = AnnotationCommandHandlerBeanPostProcessor::class.java }
                .also { it.propertyValues.add("parameterResolverFactory", getBeanReference(registry)) }
                .let { registry.registerBeanDefinition(COMMAND_HANDLER_BEAN_NAME, it) }
    }
}
