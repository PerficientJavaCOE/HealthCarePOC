package com.perfm.finddoctorapp.controller

import com.perfm.finddoctorapp.exception.DetailsNotFoundException
import com.perfm.finddoctorapp.messageservice.MessageProducer
import com.perfm.finddoctorapp.model.Doctor
import com.perfm.finddoctorapp.service.DoctorServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/doctor")
class DoctorController(private val doctorServiceImpl: DoctorServiceImpl, private val messageProducer: MessageProducer) {

    @GetMapping("/all") fun getAllDoctors(pageable: Pageable) : Page<Doctor> = doctorServiceImpl.getAll(pageable)
    @GetMapping("/{doctorId}") fun getDoctorById(@PathVariable doctorId: String ): Optional<Doctor> = doctorServiceImpl.getById(doctorId)
    @PostMapping("/add") fun insertDoctorDetails(@RequestBody doctor: Doctor) : Doctor = doctorServiceImpl.insert(doctor)
    @DeleteMapping("/delete/{id}") fun deleteDoctorById(@PathVariable id : String): Optional<Doctor> = doctorServiceImpl.deleteById(id)
}