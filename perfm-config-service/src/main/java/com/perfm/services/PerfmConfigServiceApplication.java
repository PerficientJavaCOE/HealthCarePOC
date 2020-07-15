package com.perfm.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class PerfmConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerfmConfigServiceApplication.class, args);
	}

}

