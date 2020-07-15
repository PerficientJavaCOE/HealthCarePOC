package com.perf.configuration.drools.droolsconfiguration

import org.kie.api.KieServices
import org.kie.api.runtime.KieContainer
import org.kie.internal.io.ResourceFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import java.util.*

@Configuration
@Component
class DroolsConfig {

    @Autowired lateinit var kieContainer: KieContainer

    /*@Value(value = "\${drlfile.dirpath}")
    private val dirpath: String? = null*/
    private val dirpath = "drlfile.dirpath"

    @Bean
    fun kieContainer() : KieContainer? {
        val kieServices = KieServices.Factory.get()
        val kieFileSystem = kieServices.newKieFileSystem()

        var dir: File = File("src/main/resources")
        var directoryListing: Array<File> = dir.listFiles()
        if (directoryListing != null)
            for(child in directoryListing){
                val reader = FileReader(child)
                val p = Properties()
                p.load(reader)
                if(p.contains(dirpath))
                    dir = File(p.getProperty(dirpath))
            }

        //val dir = File(dirpath)
        //dir = File("src/main/resources")
        directoryListing = dir.listFiles()
        if (directoryListing != null) {
            for(child in directoryListing)
                kieFileSystem.write(ResourceFactory.newClassPathResource(child.name));
        }
        val kieBuilder = kieServices.newKieBuilder(kieFileSystem)
        kieBuilder.buildAll()
        val kieModule = kieBuilder.kieModule
        return kieServices.newKieContainer(kieModule.releaseId)
    }

    fun validateDoctorDetails(obj: Any, responseValue:String): Response {
        val responseResult: Response = Response()
        val kieSession = kieContainer.newKieSession()
        kieSession.setGlobal(responseValue, responseResult)
        kieSession.insert(obj) // which object to validate
        kieSession.fireAllRules() // fire all rules defined into drool file (drl)
        kieSession.dispose()
        /*if (!responseResult.message.isEmpty()) {
            log.info("responseResult code is ${responseResult.code} and message is ${responseResult.message}")
        }*/
        return responseResult
    }

}