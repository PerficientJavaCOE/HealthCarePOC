package com.perfm.finddoctorapp.client

import com.perfm.finddoctorapp.model.Doctor
import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.model.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Configuration
@FeignClient(name = "DroolsEngine")
interface DroolsClient {
    @PostMapping("/api/validate/doctor") fun validateDoctorDetails(@RequestBody doctor: Doctor) : Response
    @PostMapping("/api/validate/hospital") fun validateHospitalDetails(@RequestBody hospitalDetails: HospitalDetails) : Response
}