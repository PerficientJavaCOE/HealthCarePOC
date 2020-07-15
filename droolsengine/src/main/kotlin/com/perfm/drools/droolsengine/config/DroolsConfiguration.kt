package com.perfm.drools.droolsengine.config

import org.kie.api.KieServices
import org.kie.api.runtime.KieContainer
import org.kie.internal.io.ResourceFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
@ComponentScan("com.perfm.drools.droolsengine")
class DroolsConfiguration {

    @Bean
    fun kieContainer(): KieContainer? {
        val kieServices = KieServices.Factory.get()
        val kieFileSystem = kieServices.newKieFileSystem()
        val dir = File("src/main/resources")
        val directoryListing: Array<File> = dir.listFiles()
        if (directoryListing != null) {
            for(child in directoryListing)
                kieFileSystem.write(ResourceFactory.newClassPathResource(child.name))
        }
        val kieBuilder = kieServices.newKieBuilder(kieFileSystem)
        kieBuilder.buildAll()
        val kieModule = kieBuilder.kieModule
        return kieServices.newKieContainer(kieModule.releaseId)
    }
}