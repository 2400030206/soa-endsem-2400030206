package com.soa.klef.endsen.gateway.config;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

import reactor.core.publisher.Mono;

public class TouristRoundRobinLoadBalancer implements ReactorServiceInstanceLoadBalancer {

	private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
	private final AtomicInteger position = new AtomicInteger(0);

	public TouristRoundRobinLoadBalancer(
			ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
		this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
	}

	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable();
		if (supplier == null) {
			return Mono.just(new EmptyResponse());
		}

		return supplier.get(request)
				.next()
				.map(this::chooseInstance);
	}

	private Response<ServiceInstance> chooseInstance(List<ServiceInstance> instances) {
		List<ServiceInstance> orderedInstances = instances.stream()
				.sorted(Comparator.comparingInt(ServiceInstance::getPort))
				.toList();
		if (orderedInstances.isEmpty()) {
			return new EmptyResponse();
		}

		int index = orderedInstances.size() == 1
				? 0
				: Math.floorMod(position.getAndIncrement(), orderedInstances.size());
		return new DefaultResponse(orderedInstances.get(index));
	}
}
