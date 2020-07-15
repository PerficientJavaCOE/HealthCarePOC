package com.perfm.drools.droolsengine.controller

import com.perfm.drools.droolsengine.model.Doctor
import com.perfm.drools.droolsengine.model.HospitalDetails
import com.perfm.drools.droolsengine.model.Response
import com.perfm.drools.droolsengine.service.ValidateFacts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ValidationController(@Autowired val validateFacts: ValidateFacts) {

    @PostMapping("/validate/doctor") fun validateDoctorDetails(@RequestBody doctor: Doctor) : Response = validateFacts.validateDetails(doctor)
    @PostMapping("/validate/hospital") fun validateHospitalDetails(@RequestBody hospitalDetails: HospitalDetails) : Response = validateFacts.validateDetails(hospitalDetails)
}