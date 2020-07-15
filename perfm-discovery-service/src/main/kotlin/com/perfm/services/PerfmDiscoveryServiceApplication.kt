package com.perfm.services

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer


@SpringBootApplication
@EnableEurekaServer
class PerfmDiscoveryServiceApplication

fun main(args: Array<String>) {
	runApplication<PerfmDiscoveryServiceApplication>(*args)
}
