package com.perfm.drools.droolsengine.service

import com.perfm.drools.droolsengine.model.Response
import org.kie.api.runtime.KieContainer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ValidateFacts(@Autowired val kieContainer: KieContainer) { //placeholder for all kiebases

    fun validateDetails(obj: Any): Response {
        val responseResult: Response = Response()
        val kieSession = kieContainer.newKieSession() // creates a session to call rule(s)
        kieSession.setGlobal("responseResult", responseResult) // sets global variable to return the response from rule(s)
        kieSession.insert(obj) // which object to validate
        kieSession.fireAllRules() // fire all rules defined into drool file (drl)
        kieSession.dispose()
        return responseResult
    }

}