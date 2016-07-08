package io.pivotal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

import java.util.List;

@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration {

    // core Web Flow

    @Bean
    public FlowDefinitionRegistry flowDefinitionRegistry(FlowBuilderServices flowBuilderServices) {
        return getFlowDefinitionRegistryBuilder(flowBuilderServices)
                .addFlowLocationPattern("classpath:flow/*.xml")
                .build();
    }

    @Bean
    public FlowExecutor flowExecutor(FlowDefinitionRegistry flowDefinitionRegistry) {
        return getFlowExecutorBuilder(flowDefinitionRegistry).build();
    }

    // MVC integration

    @Bean
    public FlowHandlerAdapter FlowHandlerAdapter(FlowExecutor flowExecutor) {
        FlowHandlerAdapter flowHandlerAdapter = new FlowHandlerAdapter();
        flowHandlerAdapter.setFlowExecutor(flowExecutor);
        return flowHandlerAdapter;
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping(FlowDefinitionRegistry flowDefinitionRegistry) {
        FlowHandlerMapping flowHandlerMapping = new FlowHandlerMapping();
        flowHandlerMapping.setFlowRegistry(flowDefinitionRegistry);
        flowHandlerMapping.setOrder(0);
        return flowHandlerMapping;
    }

    // MVC view resolution

    @Bean
    public FlowBuilderServices flowBuilderServices(MvcViewFactoryCreator mvcViewFactoryCreator) {
        return getFlowBuilderServicesBuilder()
                .setViewFactoryCreator(mvcViewFactoryCreator)
                .build();
    }

    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator(List<ViewResolver> viewResolvers) {
        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(viewResolvers);
        factoryCreator.setUseSpringBeanBinding(true);
        return factoryCreator;
    }

}
