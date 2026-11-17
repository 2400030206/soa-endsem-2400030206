package com.soa.klef.endsen.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.soa.klef.endsen.gateway.config.TouristLoadBalancerConfiguration;

@EnableDiscoveryClient
@LoadBalancerClient(name = "TOURIST-SERVICE", configuration = TouristLoadBalancerConfiguration.class)
@ComponentScan(excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = TouristLoadBalancerConfiguration.class))
@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
