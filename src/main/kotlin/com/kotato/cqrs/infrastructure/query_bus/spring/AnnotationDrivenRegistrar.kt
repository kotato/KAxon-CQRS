package com.kotato.cqrs.infrastructure.query_bus.spring

import org.axonframework.spring.config.annotation.SpringContextParameterResolverFactoryBuilder.getBeanReference
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata

class AnnotationDrivenRegistrar : ImportBeanDefinitionRegistrar {
    private val QUERY_HANDLER_BEAN_NAME = "__axon-annotation-query-handler-bean-post-processor"

    override fun registerBeanDefinitions(importingClassMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
        registerAnnotationQueryHandlerBeanPostProcessor(registry)
    }

    fun registerAnnotationQueryHandlerBeanPostProcessor(registry: BeanDefinitionRegistry) {
        GenericBeanDefinition()
                .also { it.beanClass = AnnotationQueryHandlerBeanPostProcessor::class.java }
                .also { it.propertyValues.add("parameterResolverFactory", getBeanReference(registry)) }
                .let { registry.registerBeanDefinition(QUERY_HANDLER_BEAN_NAME, it) }
    }
}
