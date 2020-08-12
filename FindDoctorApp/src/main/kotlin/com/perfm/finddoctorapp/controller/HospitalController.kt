package com.perfm.finddoctorapp.controller

import com.perfm.finddoctorapp.exception.HospitalDetailNotValidException
import com.perfm.finddoctorapp.exception.HospitalNotFoundException
import com.perfm.finddoctorapp.messageservice.MessageProducer
import com.perfm.finddoctorapp.model.HospitalDetails
import com.perfm.finddoctorapp.service.HospitalService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/hospital")
class HospitalController(private val hospitalService: HospitalService) {

    @GetMapping("/{id}") fun getHospitalDetailById(@PathVariable id: String): Optional<HospitalDetails> = hospitalService.getById(id)
    @GetMapping("/all") fun getAllHospitalDetails(pageable: Pageable): Page<HospitalDetails> = hospitalService.getAll(pageable)
    @PostMapping("/add") fun saveHospitalDetails(@RequestBody hospitalDetails: HospitalDetails): HospitalDetails = hospitalService.upsert(hospitalDetails)
    @DeleteMapping("/delete/{id}") fun deleteHospitalDetalsById(@PathVariable id: String): Optional<HospitalDetails> = hospitalService.deleteById(id)
    @PutMapping("/update") fun updateHospitalDetails(@RequestBody hospitalDetails: HospitalDetails): HospitalDetails = hospitalService.upsert(hospitalDetails)
}