package com.soa.klef.endsen.tourist.model;

public record TouristResponse(
		String touristName,
		String destination,
		String description,
		int instancePort) {
}
