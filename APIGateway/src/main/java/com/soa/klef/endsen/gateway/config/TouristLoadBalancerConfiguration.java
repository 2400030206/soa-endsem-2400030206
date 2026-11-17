package com.soa.klef.endsen.gateway.config;

import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration(proxyBeanMethods = false)
public class TouristLoadBalancerConfiguration {

	@Bean
	ReactorServiceInstanceLoadBalancer touristServiceLoadBalancer(
			Environment environment,
			LoadBalancerClientFactory loadBalancerClientFactory) {
		String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
		return new TouristRoundRobinLoadBalancer(
				loadBalancerClientFactory.getLazyProvider(serviceId, ServiceInstanceListSupplier.class));
	}
}
