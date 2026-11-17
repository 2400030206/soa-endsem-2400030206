package com.soa.klef.endsen.tourist.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soa.klef.endsen.tourist.model.TouristResponse;

@RestController
public class TouristController {

	private final int serverPort;

	public TouristController(@Value("${server.port}") int serverPort) {
		this.serverPort = serverPort;
	}

	@GetMapping("/tourists")
	public TouristResponse getTourist() {
		return new TouristResponse(
				"Alex Morgan",
				"Paris",
				"Visitor interested in museums and local cuisine.",
				serverPort);
	}
}
