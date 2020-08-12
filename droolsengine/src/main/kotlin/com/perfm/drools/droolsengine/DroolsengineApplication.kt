package com.perfm.drools.droolsengine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.ComponentScan

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = arrayOf("com.perfm.drools.droolsengine"))
class DroolsengineApplication

fun main(args: Array<String>) {
	runApplication<DroolsengineApplication>(*args)
}
